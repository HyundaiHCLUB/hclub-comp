package site.hclub.hyndai.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CompHistoryRequest {
    private Long    matchHistNo;
    private String  matchLoc;
    private Long    teamANo;
    private Long    ScoreA;
    private Long    teamBNo;
    private Long    ScoreB;
    private MultipartFile image;
}
