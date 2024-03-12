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

    /**
     * 작성자: 김동욱
     * 처리 내용: 매치 기록 정보를 불러옵니다.
     */
    Match getMatchVO(Long matchHistoryNo);

    GetTeamFromScoreNoResponse getTeamFromScoreNo(Long winTeamScoreNo);

    Member getLeader(Long teamNo);

    List<Member> getMembers(Long teamNo);

    /**
     * 작성자: 김동욱
     * 처리 내용: 경쟁 팀을 생성합니다.
     */
    Long addTeam(Team team);

    /**
     * 작성자: 김동욱
     * 처리 내용: 생성된 팀에 멤버들을 삽입합니다.
     */
    void addTeamMemberToTeam(MemberTeam memberTeam);

    public void updateScore(@Param("teamScoreNo") Long teamScoreNo, @Param("scoreAmount") Long scoreAmount);

    /**
     * 작성자: 김동욱
     * 처리 내용: 팀 목록 리스트를 불러옵니다.
     */
    void getTeamList(Map<String, Object> map);

    public Match getMatch(Long matchHistNo);

    public String getHistoryImageUrl(Long matchHistNo);

    /**
     * 작성자: 김동욱
     * 처리 내용: 팀 정보를 불러옵니다.
     */
    TeamDetailDTOResponse getTeamByTeamNo(Long teamNo);

    /**
     * 작성자: 김동욱
     * 처리 내용:  팀에 해당하는 멤버 리스트를 불러옵니다.
     */
    List<MemberInfo> getMemberByTeamNo(Long teamNo);

    /**
     * 작성자: 김동욱
     * 처리 내용:  멤버 정보를 불러옵니다.
     */
    List<MemberInfo> getMemberInfoWithMemberName(String memberNameInput);

    public void changeRating(@Param("teamNo") Long teamNo, @Param("ratingChange") Long ratingChange);

    public Long getTeamScoreNo(@Param("matchHistoryNo") Long matchHistoryNo, @Param("teamNo") Long teamNo);

    public Long uploadImage(UploadImageRequest request);

    public List<RankResponse> getRankList(int num);

    void updateMatchDate(@Param("matchDate") LocalDateTime matchDate, @Param("matchHistNo") Long matchHistNo);

    Long insertScore(Score score);

    Long generateMatch(CreateMatchResponse response);

    List<GetProductResponse> getProducts();

    /**
     * 작성자: 김동욱
     * 처리 내용:  경기가 날짜가 현재 날짜보다 지난 팀들을 만료 처리해줍니다.
     */
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

    /**
     * 작성자: 김동욱
     * 처리 내용: 팀끼리 매칭되었을때 매칭 처리해줍니다.
     */
    void updateIsMatched(Long teamNo);

    void updateMatchImage(@Param("matchHistoryNo") Long matchHistoryNo, @Param("imageNo") Long imageNo);
}

