package site.hclub.hyndai.mapper;


import site.hclub.hyndai.domain.MatchVO;
import site.hclub.hyndai.domain.Member;
import site.hclub.hyndai.domain.MemberTeam;
import site.hclub.hyndai.domain.Team;

import java.util.List;

public interface CompMapper {

    MatchVO getMatchVO(Long matchHistoryNo);

    Team getTeamFromScoreNo(Long winTeamScoreNo);

    Member getLeader(Long teamNo);

    List<Member> getMembers(Long teamNo);


    public Long addTeam(Team team);

    public void addTeamMemberToTeam(MemberTeam memberTeam);

}
