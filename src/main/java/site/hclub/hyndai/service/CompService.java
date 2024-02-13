package site.hclub.hyndai.service;


import org.springframework.web.multipart.MultipartFile;
import site.hclub.hyndai.dto.CreateTeamDTO;
import site.hclub.hyndai.dto.MatchDetailResponse;

import java.io.IOException;

public interface CompService {

    MatchDetailResponse getMatchDetail(Long matchHistoryNo);


    public void makeTeam(CreateTeamDTO teamDTO, MultipartFile multipartFile) throws IOException;

}
