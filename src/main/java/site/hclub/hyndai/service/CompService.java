package site.hclub.hyndai.service;


import org.springframework.web.multipart.MultipartFile;

import site.hclub.hyndai.dto.SettleDTO;
import site.hclub.hyndai.dto.request.*;
import site.hclub.hyndai.dto.response.*;
import site.hclub.hyndai.dto.response.HistoryDetailResponse;
import site.hclub.hyndai.dto.response.MatchDetailResponse;
import site.hclub.hyndai.dto.response.RankResponse;

import java.io.IOException;
import java.util.List;

public interface CompService {

    MatchDetailResponse getMatchDetail(Long matchHistoryNo);

    public CreateTeamResponse makeTeam(CreateTeamRequest teamDTO, MultipartFile multipartFile) throws IOException;

    public GetTeamDetailResponse getTeamDetail(Long teamNo);

    public void updateScore(Long teamNo, Long score);

    public void uploadHistoryImage(MultipartFile multipartFile) throws IOException;

    public HistoryDetailResponse getHistoryDetail(Long matchHistNo);

    public void modifyMatchHistory(HistoryModifyRequest request);

    public List<Long> updateRating(AfterMatchRatingRequest request);

    public GetMemberInfoResponse getMemberInfo(String memberName);

    public GetTeamListResponse getTeamList(PageRequestDTO pageRequestDTO);


    List<RankResponse> getRankList(int num);

    void updateMatchDate(String matchDate, Long matchHistNo);

    void generateMatch(CreateMatchRequest request);

    /**
	 작성자: 김은솔 
	 처리 내용: 결제 정보를 삽입한다.
	*/
	int insertSettle(SettleDTO sdto);
}
