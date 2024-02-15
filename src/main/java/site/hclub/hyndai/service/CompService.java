package site.hclub.hyndai.service;


import org.springframework.web.multipart.MultipartFile;
import site.hclub.hyndai.dto.*;
import site.hclub.hyndai.dto.request.AfterMatchRatingRequest;
import site.hclub.hyndai.dto.request.HistoryModifyRequest;
import site.hclub.hyndai.dto.response.HistoryDetailResponse;
import site.hclub.hyndai.dto.response.MatchDetailResponse;

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


}
