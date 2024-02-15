package site.hclub.hyndai.dto.request;

import lombok.Data;

@Data
public class HistoryModifyRequest {
    private Long    matchHistNo;
    private Long    winTeamScoreNo;
    private Long    winTeamScoreAmount;
    private Long    loseTeamScoreNo;
    private Long    loseTeamScoreAmount;
}
