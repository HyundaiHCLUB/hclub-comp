package site.hclub.hyndai.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.hclub.hyndai.common.util.AmazonS3Service;
import site.hclub.hyndai.common.util.EloService;
import site.hclub.hyndai.common.util.TimeService;
import site.hclub.hyndai.domain.*;
import site.hclub.hyndai.dto.MemberInfo;
import site.hclub.hyndai.dto.request.*;
import site.hclub.hyndai.dto.response.HistoryDetailResponse;
import site.hclub.hyndai.dto.response.MatchDetailResponse;
import site.hclub.hyndai.dto.response.RankResponse;
import site.hclub.hyndai.dto.response.TeamDetailResponse;
import site.hclub.hyndai.dto.*;
import site.hclub.hyndai.dto.response.*;
import site.hclub.hyndai.mapper.CompMapper;
import site.hclub.hyndai.mapper.MemberMapper;

import java.io.IOException;
import java.time.LocalDateTime;
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

    @Autowired
    TimeService timeService;

    @Autowired
    EloService eloService;

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
    public CreateTeamResponse makeTeam(CreateTeamRequest teamDTO, MultipartFile multipartFile) throws IOException {

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

        Team team = Team.builder()
                .teamLoc(teamDTO.getTeamLoc())
                .teamName(teamDTO.getTeamName())
                .teamGoods(teamDTO.getTeamGoods())
                .matchCapacity(teamDTO.getMatchCapacity())
                .matchDate(timeService.parseStringToLocalDateTime(teamDTO.getMatchDate()))
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

        CreateTeamResponse teamResponse = CreateTeamResponse.builder()
                .teamNo(team.getTeamNo())
                .teamName(team.getTeamName())
                .teamLoc(team.getTeamLoc())
                .teamGoods(team.getTeamGoods())
                .matchType(team.getMatchType())
                .matchCapacity(team.getMatchCapacity()).build();

        return teamResponse;
    }

    @Override
    public GetTeamDetailResponse getTeamDetail(Long teamNo) {
        GetTeamDetailResponse teamDetailResponse = compMapper.getTeamByTeamNo(teamNo);

        return teamDetailResponse;
    }

    @Override
    public GetMemberInfoResponse getMemberInfo(String memberName) {
        GetMemberInfoResponse res = new GetMemberInfoResponse();
        List<MemberInfo> rDTO = compMapper.getMemberInfoWithMemberName(memberName);
        res.setMemberList(rDTO);
        return res;
    }

    // 팀 페이지 네이션
    @Override
    public GetTeamListResponse getTeamList(PageRequestDTO pageRequestDTO) {

        return null;
    }

    /*
     * @작성자 : 송원선
     * 경기 스코어 score 테이블에 기록(팀별)
     * */
    @Override
    public void updateScore(Long teamNo, Long score) {
        Long teamScoreNo = compMapper.getTeamScoreNo(teamNo);
        compMapper.updateScore(teamScoreNo, score);
    }

    // 경기 기록 이미지 업로드
    @Override
    public void uploadHistoryImage(MultipartFile multipartFile) throws IOException {
        String url;
        /* S3 에 파일 업로드 */
        if (multipartFile == null){ // 이미지 null 인 경우 -> 기본 이미지로 대체
            url = "https://h-clubbucket.s3.ap-northeast-2.amazonaws.com/default/team.png";
        }else {
            String filePath = "team";
            List<MultipartFile> multipartFiles = new ArrayList<>();
            multipartFiles.add(multipartFile);
            // uploadFiles 메서드를 사용하여 파일 업로드
            List<String> urls = amazonS3Service.uploadFiles(filePath, multipartFiles);
            // 반환된 URL 리스트에서 첫 번째 URL을 사용
            url = urls.get(0);
        }
        log.info(url);
        /* DB 에 파일 URL 업로드*/
        String fileName = multipartFile.getOriginalFilename();
        compMapper.uploadImage(fileName, url);
        System.out.println("url -> : " + url);
    }

    // 경기 상세 기록 조회
    @Override
    public HistoryDetailResponse getHistoryDetail(Long matchHistNo) {

        HistoryDetailResponse response = new HistoryDetailResponse();
        Match match = compMapper.getMatch(matchHistNo);
        Team winTeam = compMapper.getTeamFromScoreNo(match.getWinTeamScoreNo());
        Team loseTeam = compMapper.getTeamFromScoreNo(match.getLoseTeamScoreNo());
        String imageUrl = compMapper.getHistoryImageUrl(matchHistNo);
        String matchDate = timeService.parseLocalDateTimeToString(match.getMatchDate());
        response.setMatchHistoryNo(matchHistNo);
        response.setMatchLoc(match.getMatchLoc());
        response.setWinTeam(winTeam);
        response.setLoseTeam(loseTeam);
        response.setImageUrl(imageUrl);
        response.setMatchDate(matchDate);
        return  response;
    }

    // 경기 기록 수정
    @Override
    public void modifyMatchHistory(HistoryModifyRequest request) {
        /* score 테이블에 있는 점수 컬럼(score_amount) 변경 */
        // 1. 이긴 팀 점수 변경
        Long winTeamScoreNo = request.getWinTeamScoreNo();
        Long winTeamScoreAmount = request.getWinTeamScoreAmount();
        compMapper.updateScore(winTeamScoreNo, winTeamScoreAmount);
        // 2. 진 팀 점수 변경
        Long loseTeamScoreNo = request.getLoseTeamScoreNo();
        Long loseTeamScoreAmount = request.getLoseTeamScoreAmount();
        compMapper.updateScore(loseTeamScoreNo, loseTeamScoreAmount);
    }

    // 경기 종료 후 레이팅 변경
    @Override
    public List<Long> updateRating(AfterMatchRatingRequest request) {
        // 경기 및 팀 정보
        Match match = compMapper.getMatch(request.getMatchHistNo());
        Team winTeam = compMapper.getTeamFromScoreNo(match.getWinTeamScoreNo());
        Team loseTeam = compMapper.getTeamFromScoreNo(match.getLoseTeamScoreNo());
        // 각 팀 기존 레이팅
        Long winTeamRating = winTeam.getTeamRating();
        Long loseTeamRating = loseTeam.getTeamRating();
        // 점수 비교
        String result = "";
        if(request.getWinTeamScore() == request.getLoseTeamScore()) {
            result = "DRAW";
        }else{
            result = "WIN";
        }
        log.info("match result ==> " + request.getWinTeamNo() + " team " + result);
        Long ratingChange = eloService.getRatingChange(winTeamRating, loseTeamRating, result);
        Long winTeamChange = ratingChange - winTeamRating;
        Long loseTeamChange = (ratingChange - loseTeamRating) * (-1);

        compMapper.changeRating(winTeam.getTeamNo(), winTeamChange);
        compMapper.changeRating(loseTeam.getTeamNo(), loseTeamChange);
        List<Long> changes = new ArrayList<>();
        changes.add(winTeamChange);
        changes.add(loseTeamChange);
        return changes;
    }

    // 상위 10명 리스트 리턴
    @Override
    public List<RankResponse> getRankList(int num) {
        List<RankResponse> list;
        list = compMapper.getRankList(num);
        return list;
    }
    // 경기 일자 등록
    @Override
    public void updateMatchDate(String matchDateString, Long matchHistNo) {
        LocalDateTime matchDate = timeService.parseStringToLocalDateTime(matchDateString);

        compMapper.updateMatchDate(matchDate, matchHistNo);
    }

    // 경기 생성
    @Override
    public void generateMatch(CreateMatchRequest request) {
        Long team1No = request.getTeam1No();
        Long team2No = request.getTeam2No();
        try {
            // 1. score 테이블에 추가
            Score score1 = new Score();
            Score score2 = new Score();
            score1.setTeamNo(team1No);
            score2.setTeamNo(team2No);
            compMapper.insertScore(score1);
            compMapper.insertScore(score2);
            Long scoreNo1 = score1.getScoreNo();
            Long scoreNo2 = score2.getScoreNo();
            log.info(" === Service ===");
            log.info("scoreNo1 : " + scoreNo1);
            log.info("scoreNo2 : " + scoreNo2);
            // 2. match 테이블에 연결
            compMapper.generateMatch(scoreNo1, scoreNo2, request.getMatchLoc());
        }catch (Exception e){
            log.error(e.getMessage());
        }

    }


}
