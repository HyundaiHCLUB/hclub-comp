package site.hclub.hyndai.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import site.hclub.hyndai.domain.*;
import site.hclub.hyndai.dto.MemberInfo;
import site.hclub.hyndai.dto.TeamDTO;
import site.hclub.hyndai.dto.response.GetProductResponse;
import site.hclub.hyndai.dto.response.MatchingResponse;
import site.hclub.hyndai.dto.response.RankResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface CompMapper {

    Match getMatchVO(Long matchHistoryNo);

    Team getTeamFromScoreNo(Long winTeamScoreNo);

    Member getLeader(Long teamNo);

    List<Member> getMembers(Long teamNo);

    public Long addTeam(Team team);

    public void addTeamMemberToTeam(MemberTeam memberTeam);

    public void updateScore(@Param("teamScoreNo") Long teamScoreNo, @Param("scoreAmount") Long scoreAmount);

    public void getTeamList(Map<String, Object> map);

    public Match getMatch(Long matchHistNo);

    public String getHistoryImageUrl(Long matchHistNo);

    public TeamDTO getTeamByTeamNo(Long teamNo);

    public List<MemberInfo> getMemberInfoWithMemberName(String memberNameInput);

    public void changeRating(@Param("teamNo") Long teamNo, @Param("ratingChange") Long ratingChange);

    public Long getTeamScoreNo(Long teamNo);

    public void uploadImage(@Param("fileName") String fileName, @Param("url") String url);

    public List<RankResponse> getRankList(int num);

    void updateMatchDate(@Param("matchDate") LocalDateTime matchDate, @Param("matchHistNo") Long matchHistNo);

    Long insertScore(Score score);

    void generateMatch(@Param("scoreNo1") Long scoreNo1, @Param("scoreNo2") Long scoreNo2, @Param("matchLoc") String matchLoc);

    List<GetProductResponse> getProducts();

    @Update("UPDATE team SET ISMATCHED = 'Y' WHERE MATCH_DATE < SYSDATE")
    void updateMatchStatus();

    List<Long> getTeamMemberList(Long teamNo);

    List<MatchingResponse> getTeams();

    void updateMatchLoc(@Param("matchHistoryNo") Long matchHistoryNo, @Param("matchLoc") String matchLoc);
}
