package site.hclub.hyndai.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import site.hclub.hyndai.domain.*;
import site.hclub.hyndai.dto.MemberInfo;
import site.hclub.hyndai.dto.TeamDTO;
import site.hclub.hyndai.dto.request.UploadImageRequest;
import site.hclub.hyndai.dto.response.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface CompMapper {

    Match getMatchVO(Long matchHistoryNo);

    GetTeamFromScoreNoResponse getTeamFromScoreNo(Long winTeamScoreNo);

    Member getLeader(Long teamNo);

    List<Member> getMembers(Long teamNo);

    public Long addTeam(Team team);

    public void addTeamMemberToTeam(MemberTeam memberTeam);

    public void updateScore(@Param("teamScoreNo") Long teamScoreNo, @Param("scoreAmount") Long scoreAmount);

    public void getTeamList(Map<String, Object> map);

    public Match getMatch(Long matchHistNo);

    public String getHistoryImageUrl(Long matchHistNo);

    public TeamDTO getTeamByTeamNo(Long teamNo);

    public List<MemberInfo> getMemberByTeamNo(Long teamNo);

    public List<MemberInfo> getMemberInfoWithMemberName(String memberNameInput);

    public void changeRating(@Param("teamNo") Long teamNo, @Param("ratingChange") Long ratingChange);

    public Long getTeamScoreNo(@Param("matchHistoryNo") Long matchHistoryNo, @Param("teamNo") Long teamNo);

    public Long uploadImage(UploadImageRequest request);

    public List<RankResponse> getRankList(int num);

    void updateMatchDate(@Param("matchDate") LocalDateTime matchDate, @Param("matchHistNo") Long matchHistNo);

    Long insertScore(Score score);

    Long generateMatch(CreateMatchResponse response);

    List<GetProductResponse> getProducts();

    @Update("UPDATE team SET ISMATCHED = 'Y' WHERE MATCH_DATE < SYSDATE")
    void updateMatchStatus();

    List<Long> getTeamMemberList(Long teamNo);

    List<MatchingResponse> getTeams();

    void updateMatchLoc(@Param("matchHistoryNo") Long matchHistoryNo, @Param("matchLoc") String matchLoc);

    Long findMemberNo(String memberId);

    LoseTeamSettleResponse getLoseTeamSettleInfo(Long matchHistNo);

    WinTeamSettleResponse getWinTeamSettleInfo(Long matchHistNo);

    List<String> getTeamMemberIds(Long teamNo);

    Team getTeamInfo(Long teamNo);

    void updateIsMatched(Long teamNo);

    void updateMatchImage(@Param("matchHistoryNo") Long matchHistoryNo, @Param("imageNo") Long imageNo);
}

