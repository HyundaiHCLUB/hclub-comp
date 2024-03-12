package site.hclub.hyndai.service;


import org.springframework.web.multipart.MultipartFile;
import site.hclub.hyndai.domain.Team;
import site.hclub.hyndai.dto.SettleDTO;
import site.hclub.hyndai.dto.TeamDTO;
import site.hclub.hyndai.dto.request.*;
import site.hclub.hyndai.dto.response.*;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface CompService {

    /**
     * 작성자: 송원선
     * 처리 내용:  경기 상세정보 보기
     */
    MatchDetailResponse getMatchDetail(Long matchHistoryNo);

    /**
     * 작성자: 김동욱
     * 처리 내용: 경쟁 - 팀 생성
     */
    public CreateTeamResponse makeTeam(CreateTeamRequest teamDTO, MultipartFile multipartFile) throws IOException;

    /**
     * 작성자: 김동욱
     * 처리 내용: 경쟁 - 팀 상세보기
     */
    public TeamDetailDTOResponse getTeamDetail(Long teamNo);

    /**
     * 작성자: 송원선
     * 처리 내용:  점수 수정
     */
    public void updateScore(Long matchHistoryNo, Long teamNo, Long score);

    /**
     * 작성자: 송원선
     * 처리 내용:  경기 이미지 업로드
     */
    public void uploadHistoryImage(Long matchHistoryNo, MultipartFile multipartFile) throws IOException;

    /**
     * 작성자: 송원선
     * 처리 내용:  경기 기록 상세보기
     */
    public HistoryDetailResponse getHistoryDetail(Long matchHistNo);

    /**
     * 작성자: 송원선
     * 처리 내용:  경기 기록 수정
     */
    public void modifyMatchHistory(HistoryModifyRequest request);

    /**
     * 작성자: 송원선
     * 처리 내용:  레이팅 변경
     */
    public List<Long> updateRating(AfterMatchRatingRequest request);

    /**
     * 작성자: 김동욱
     * 처리 내용: 경쟁 - 멤버 정보 조회
     */

    GetMemberInfoResponse getMemberInfo(String memberName);

    /**
     * 작성자: 김동욱
     * 처리 내용: 경쟁 - 팀 목록 조회
     */
    List<TeamDTO> getTeamList(PageRequestDTO pageRequestDTO);

    /**
     * 작성자: 송원선
     * 처리 내용:  랭킹 리스트
     */
    List<RankResponse> getRankList(int num);

    /**
     * 작성자: 송원선
     * 처리 내용:  경기 일자 수정
     */
    void updateMatchDate(String matchDate, Long matchHistNo);

    /**
     * 작성자: 송원선
     * 처리 내용:  경기 생성
     */
    Long generateMatch(CreateMatchRequest request);

    /**
     * 작성자: 김동욱
     * 처리 내용: 경쟁 - 만료된 팀 처리
     */
    void updateMatchStatus();

    /**
     * 작성자: 김동욱
     * 처리 내용: 경쟁 - 상품 목록 조회
     */
    List<GetProductResponse> getProducts();

    /**
     * 작성자: 김은솔
     * 처리 내용: 결제 정보를 삽입한다.
     */
    int insertSettle(SettleDTO sdto);

    /**
     * 작성자: 송원선
     * 처리 내용:  팀의 멤버 목록(번호) 리턴
     */
    List<Long> getTeamMemberList(Long teamNo);
    /**
     * 작성자: 송원선
     * 처리 내용:  경기 장소 업데이트
     */
    void updateMatchLocation(UpdateMatchLocationRequest request);

    /**
     * 작성자: 이혜연
     * 처리 내용:  사용자의 번호(PK) 찾기
     */
    Long findMemberNo(String memberId);


    /**
     * 작성자: 김은솔
     * 처리 내용: 카카오페이 API를 호출한다.
     */
	String kakaopay(HttpSession session, SettleDTO sdto);

    /**
     * 작성자: 송원선
     * 처리 내용:  결제 필요 정보 리턴
     */
    SettleResponse getSettleInfo(Long matchHistNo);

    /**
     * 작성자: 송원선
     * 처리 내용:  (매치) 두 팀중 로그인 사용자가 속한 팀의 번호 리턴
     */
    Long getMyTeamNo(ConfigureTeamRequest request);
    /**
     * 작성자: 송원선
     * 처리 내용:  팀 정보 조회
     */
    Team getTeamInfo(Long teamNo);

}
