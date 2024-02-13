package site.hclub.hyndai.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.hclub.hyndai.common.util.AmazonS3Service;
import site.hclub.hyndai.domain.Match;
import site.hclub.hyndai.domain.Member;
import site.hclub.hyndai.domain.MemberTeam;
import site.hclub.hyndai.domain.Team;
import site.hclub.hyndai.dto.CreateTeamDTO;
import site.hclub.hyndai.dto.MatchDetailResponse;
import site.hclub.hyndai.dto.TeamDetailResponse;
import site.hclub.hyndai.mapper.CompMapper;
import site.hclub.hyndai.mapper.MemberMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CompServiceImpl implements CompService {

    @Autowired
    CompMapper compMapper;

    @Autowired
    AmazonS3Service amazonS3Service;

    @Autowired
    MemberMapper memberMapper;

    @Override
    public MatchDetailResponse getMatchDetail(Long matchHistoryNo) {
        MatchDetailResponse dto = new MatchDetailResponse();
        try {
            dto.setMatchHistoryNo(matchHistoryNo);
            Match vo = compMapper.getMatchVO(matchHistoryNo); // 여기서 vo null 리턴
            // matchVO 의 win_team_score_no, lose_team_score_no 로 팀 정보 참조
            Long winTeamScoreNo = vo.getWinTeamScoreNo();
            Long loseTeamScoreNo = vo.getWinTeamScoreNo();

            // team1 정보
            Team team1 = compMapper.getTeamFromScoreNo(winTeamScoreNo);
            TeamDetailResponse team1DTO = setTeamDTO(team1);
            // team2 정보
            Team team2 = compMapper.getTeamFromScoreNo(loseTeamScoreNo);
            TeamDetailResponse team2DTO = setTeamDTO(team2);
            dto.setTeam1(team1DTO);
            dto.setTeam2(team2DTO);

            log.info("team1 -> " + team1DTO.toString());
            log.info("team2 -> " + team2DTO.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return dto;
    }


    /*
     * @작성자 : 송원선
     * Team(VO) -> TeamDetailResponse
     */
    public TeamDetailResponse setTeamDTO(Team team) {
        TeamDetailResponse dto = new TeamDetailResponse();
        // vo 에 있는 정보들
        dto.setTeamNo(team.getTeamNo());
        dto.setTeamLoc(team.getTeamLoc());
        dto.setTeamName(team.getTeamName());
        dto.setTeamGoods(team.getTeamGoods());
        dto.setMatchType(team.getMatchType());
        dto.setMatchCapacity(team.getMatchCapacity());
        /* vo 에 없는 정보들 */
        // 리더
        Member leader = compMapper.getLeader(team.getTeamNo());
        dto.setLeader(leader);
        // 팀원 (리더 제외) List
        List<Member> members = compMapper.getMembers(team.getTeamNo());
        dto.setMembers(members);
        // 팀 레이팅 -> 멤버들 레이팅 평균
        int teamRating = 0;
        teamRating += leader.getMemberRating();
        for (Member member : members) {
            teamRating += member.getMemberRating();
        }
        teamRating = (int) (teamRating / (members.size() + 1));
        dto.setTeamRating(teamRating);
        return dto;
    }


    @Override
    public void makeTeam(CreateTeamDTO teamDTO, MultipartFile multipartFile) throws IOException {

        List<MultipartFile> multipartFileList = new ArrayList<>();
        List<String> urlList = new ArrayList<>();
        if (multipartFile == null) {
            urlList.add("https://h-clubbucket.s3.ap-northeast-2.amazonaws.com/default/team.png");
        } else {
            multipartFileList.add(multipartFile);
            log.info(multipartFileList.toString());
            urlList = amazonS3Service.uploadFiles("team", multipartFileList);
        }
        // 팀 레이팅 계산
        ArrayList<Long> memberList = teamDTO.getMemberList();
        Long memberRatingSum = 0L;
        for (Long memberNo : memberList) {
            log.info(memberNo + " --");
            Long rating = memberMapper.getMemberRating(memberNo);
            memberRatingSum += rating;

        }
        long teamRating = memberRatingSum / memberList.size();
        teamRating = Math.round(teamRating);
        log.info(teamRating + "");
        Team team = Team.builder()
                .teamLoc(teamDTO.getTeamLoc())
                .teamName(teamDTO.getTeamName())
                .teamGoods(teamDTO.getTeamGoods())
                .matchCapacity(teamDTO.getMatchCapacity())
                .teamImage(urlList.get(0))
                .teamRating(teamRating)
                .matchType(teamDTO.getMatchType()).build();

        compMapper.addTeam(team);
        Long teamNo = team.getTeamNo();
        log.info(teamNo + "==================");

        // 멤버 삽입


        for (int i = 0; i < memberList.size(); i++) {
            Long memberNo = memberList.get(i);
            MemberTeam memberTeam = MemberTeam.builder()
                    .teamNo(teamNo)
                    .memberNo(memberNo).build();
            // 맨 처음 들어가는 사람 리더
            if (i == 0) {
                memberTeam.setIsLeader("Y");
            } else {
                memberTeam.setIsLeader("N");
            }
            compMapper.addTeamMemberToTeam(memberTeam);
        }


    }

    /*
     * @작성자 : 송원선
     * 경기 스코어 score 테이블에 기록(팀별)
     * */
    @Override
    public void updateScore(Long teamNo, Long score) {
        compMapper.updateScore(teamNo, score);
    }
}
