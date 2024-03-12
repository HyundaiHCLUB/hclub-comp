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

    /**
     * 작성자: 송원선
     * 처리 내용:  점수 번호 에 해당하는 팀의 정보를 불러옵니다.
     */
    GetTeamFromScoreNoResponse getTeamFromScoreNo(Long winTeamScoreNo);

    /**
     * 작성자: 송원선
     * 처리 내용:  해당 팀 팀장의 정보를 불러옵니다
     */
    Member getLeader(Long teamNo);
    /**
     * 작성자: 송원선
     * 처리 내용:  해당 팀 팀원(팀장 제외) 정보를 불러옵니다
     */
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

    /**
     * 작성자: 송원선
     * 처리 내용: 레이팅을 변화시킵니다
     */
    public void changeRating(@Param("teamNo") Long teamNo, @Param("ratingChange") Long ratingChange);

    /**
     * 작성자: 송원선
     * 처리 내용:  팀의 scoreNo 를 반환합니다
     */
    public Long getTeamScoreNo(@Param("matchHistoryNo") Long matchHistoryNo, @Param("teamNo") Long teamNo);

    /**
     * 작성자: 송원선
     * 처리 내용:  DB에 S3에 업로드된 이미지 URL 을 저장합니다
     */
    public Long uploadImage(UploadImageRequest request);

    /**
     * 작성자: 송원선
     * 처리 내용: num 명의 랭킹 리스트를 불러옵니다
     */
    public List<RankResponse> getRankList(int num);

    /**
     * 작성자: 송원선
     * 처리 내용:  경기 일자를 업데이트 합니다
     */
    void updateMatchDate(@Param("matchDate") LocalDateTime matchDate, @Param("matchHistNo") Long matchHistNo);

    /**
     * 작성자: 송원선
     * 처리 내용: score 테이블에 레코드를 생성합니다
     */
    Long insertScore(Score score);

    /**
     * 작성자: 송원선
     * 처리 내용:  경기를 생성합니다
     */
    Long generateMatch(CreateMatchResponse response);

    List<GetProductResponse> getProducts();

    /**
     * 작성자: 김동욱
     * 처리 내용:  경기가 날짜가 현재 날짜보다 지난 팀들을 만료 처리해줍니다.
     */
    @Update("UPDATE team SET ISMATCHED = 'Y' WHERE MATCH_DATE < SYSDATE")
    void updateMatchStatus();

    /**
     * 작성자: 송원선
     * 처리 내용: 해당 팀의 멤버 목록을 (멤버 번호 리스트) 가져옵니다
     */
    List<Long> getTeamMemberList(Long teamNo);

    /**
     * 작성자: 이혜연
     * 처리 내용:  (매칭) 팀들의 정보를 불러옵니다
     */
    List<MatchingResponse> getTeams();

    /**
     * 작성자: 송원선
     * 처리 내용:  경기 장소를 수정합니다
     */
    void updateMatchLoc(@Param("matchHistoryNo") Long matchHistoryNo, @Param("matchLoc") String matchLoc);

    Long findMemberNo(String memberId);

    /**
     * 작성자: 송원선
     * 처리 내용:  결제정보 - 진팀 관련 데이터를 불러옵니다
     */
    LoseTeamSettleResponse getLoseTeamSettleInfo(Long matchHistNo);

    /**
     * 작성자: 송원선
     * 처리 내용:  결제정보 - 이긴팀 관련 데이터를 불러옵니다
     */
    WinTeamSettleResponse getWinTeamSettleInfo(Long matchHistNo);

    /**
     * 작성자: 송원선
     * 처리 내용: 팀의 멤버 아이디들을 가져옵니다
     */
    List<String> getTeamMemberIds(Long teamNo);

    /**
     * 작성자: 송원선
     * 처리 내용:  팀의 정보를 가져옵니다 (Team VO)
     */
    Team getTeamInfo(Long teamNo);

    /**
     * 작성자: 김동욱
     * 처리 내용: 팀끼리 매칭되었을때 매칭 처리해줍니다.
     */
    void updateIsMatched(Long teamNo);

    /**
     * 작성자: 송원선
     * 처리 내용:  경기 이미지를 수정합니다
     */
    void updateMatchImage(@Param("matchHistoryNo") Long matchHistoryNo, @Param("imageNo") Long imageNo);
}

