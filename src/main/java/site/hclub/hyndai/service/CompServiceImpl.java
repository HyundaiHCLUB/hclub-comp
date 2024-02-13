package site.hclub.hyndai.service;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.hclub.hyndai.domain.MatchVO;
import site.hclub.hyndai.domain.Member;
import site.hclub.hyndai.domain.Team;
import site.hclub.hyndai.dto.MatchDetailResponse;
import site.hclub.hyndai.dto.TeamDetailResponse;
import site.hclub.hyndai.mapper.CompMapper;

import java.util.List;

@Service
@Log4j
public class CompServiceImpl implements CompService {

    @Autowired
    CompMapper compMapper;

   /*
    * @작성자 : 송원선
    */
    @Override
    public MatchDetailResponse getMatchDetail(Long matchHistoryNo) {
        MatchDetailResponse dto = new MatchDetailResponse();
        try {
            dto.setMatchHistoryNo(matchHistoryNo);
            MatchVO vo = compMapper.getMatchVO(matchHistoryNo); // 여기서 vo null 리턴
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
        }catch (Exception e) {
            log.error(e.getMessage());
        }

        return dto;
    }

    /*
     * @작성자 : 송원선
     * Team(VO) -> TeamDetailResponse
     */
    public TeamDetailResponse setTeamDTO(Team team){
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
        for(Member member : members) {
            teamRating += member.getMemberRating();
        }
        teamRating = (int)(teamRating / (members.size() + 1));
        dto.setTeamRating(teamRating);
        return dto;
    }
}
