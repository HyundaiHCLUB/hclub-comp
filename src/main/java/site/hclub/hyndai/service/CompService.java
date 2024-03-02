package site.hclub.hyndai.service;


import org.springframework.web.multipart.MultipartFile;
import site.hclub.hyndai.dto.SettleDTO;
import site.hclub.hyndai.dto.TeamDTO;
import site.hclub.hyndai.dto.request.*;
import site.hclub.hyndai.dto.response.*;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface CompService {

    MatchDetailResponse getMatchDetail(Long matchHistoryNo);

    public CreateTeamResponse makeTeam(CreateTeamRequest teamDTO, MultipartFile multipartFile) throws IOException;

    public TeamDTO getTeamDetail(Long teamNo);

    public void updateScore(Long teamNo, Long score);

    public void uploadHistoryImage(MultipartFile multipartFile) throws IOException;

    public HistoryDetailResponse getHistoryDetail(Long matchHistNo);

    public void modifyMatchHistory(HistoryModifyRequest request);

    public List<Long> updateRating(AfterMatchRatingRequest request);

    public GetMemberInfoResponse getMemberInfo(String memberName);

    public List<TeamDTO> getTeamList(PageRequestDTO pageRequestDTO);


    List<RankResponse> getRankList(int num);

    void updateMatchDate(String matchDate, Long matchHistNo);

    void generateMatch(CreateMatchRequest request);

    // 3분 단위로 시간이 지난 경기를 제거합니다.
    public void updateMatchStatus();

    public List<GetProductResponse> getProducts();

    /**
     * 작성자: 김은솔
     * 처리 내용: 결제 정보를 삽입한다.
     */
    int insertSettle(SettleDTO sdto);

    // 팀의 멤버 목록 리턴
    List<Long> getTeamMemberList(Long teamNo);
    // 경기 장소 업데이트
    void updateMatchLocation(UpdateMatchLocationRequest request);

    Long findMemberNo(String memberId);


    /**
     * 작성자: 김은솔
     * 처리 내용: 카카오페이 API를 호출한다.
     */
	String kakaopay(HttpSession session, SettleDTO sdto);

    SettleResponse getSettleInfo(Long matchHistNo);

    // (매치) 두 팀중 로그인 사용자가 속한 팀의 번호 리턴
    Long getMyTeamNo(ConfigureTeamRequest request);
}
