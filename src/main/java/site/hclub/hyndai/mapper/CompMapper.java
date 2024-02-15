package site.hclub.hyndai.mapper;


import org.apache.ibatis.annotations.Param;
import site.hclub.hyndai.domain.Match;
import site.hclub.hyndai.domain.Member;
import site.hclub.hyndai.domain.MemberTeam;
import site.hclub.hyndai.domain.Team;

import java.util.List;

public interface CompMapper {

    Match getMatchVO(Long matchHistoryNo);

    Team getTeamFromScoreNo(Long winTeamScoreNo);

    Member getLeader(Long teamNo);

    List<Member> getMembers(Long teamNo);

    public Long addTeam(Team team);

    public void addTeamMemberToTeam(MemberTeam memberTeam);

    public void updateScore(@Param("teamScoreNo") Long teamScoreNo, @Param("scoreAmount") Long scoreAmount);


    public Match getMatch(Long matchHistNo);

    public String getHistoryImageUrl(Long matchHistNo);

    public void changeRating(@Param("teamNo") Long teamNo, @Param("ratingChange") Long ratingChange);

    public Long getTeamScoreNo(Long teamNo);
}
