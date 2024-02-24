package site.hclub.hyndai.service;


import org.springframework.web.multipart.MultipartFile;
import site.hclub.hyndai.dto.SettleDTO;
import site.hclub.hyndai.dto.TeamDTO;
import site.hclub.hyndai.dto.request.*;
import site.hclub.hyndai.dto.response.*;

import java.io.IOException;
import java.util.List;

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


    /**
     * 작성자: 김은솔
     * 처리 내용: 결제 정보를 삽입한다.
     */
    int insertSettle(SettleDTO sdto);

    // 팀의 멤버 목록 리턴
    List<Long> getTeamMemberList(Long teamNo);
}
