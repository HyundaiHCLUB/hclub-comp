package site.hclub.hyndai.dto.request;

import lombok.Data;

@Data
public class AfterMatchRatingRequest {
    private Long    matchHistNo;
    private Long    winTeamNo;
    private Long    winTeamScore;
    private Long    loseTeamNo;
    private Long    loseTeamScore;
}
