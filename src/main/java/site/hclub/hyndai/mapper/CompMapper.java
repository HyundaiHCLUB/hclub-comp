package site.hclub.hyndai.mapper;


import org.apache.ibatis.annotations.Param;
import site.hclub.hyndai.domain.*;
import site.hclub.hyndai.dto.MemberInfo;
import site.hclub.hyndai.dto.response.GetTeamDetailResponse;
import site.hclub.hyndai.dto.response.RankResponse;

import java.time.LocalDateTime;
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

    public GetTeamDetailResponse getTeamByTeamNo(Long teamNo);

    public List<MemberInfo> getMemberInfoWithMemberName(String memberNameInput);

    public void changeRating(@Param("teamNo") Long teamNo, @Param("ratingChange") Long ratingChange);

    public Long getTeamScoreNo(Long teamNo);

    public void uploadImage(@Param("fileName") String fileName, @Param("url") String url);

    public List<RankResponse> getRankList(int num);

    void updateMatchDate(@Param("matchDate") LocalDateTime matchDate, @Param("matchHistNo") Long matchHistNo);

    Long insertScore(Score score);

    void generateMatch(@Param("scoreNo1") Long scoreNo1, @Param("scoreNo2") Long scoreNo2,@Param("matchLoc") String matchLoc);
}
