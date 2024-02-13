package site.hclub.hyndai.domain;

import lombok.Data;

@Data
public class Match {
    private Long matchHistoryNo;
    private String matchLoc;
    private Long winTeamScoreNo;
    private Long loseTeamScoreNo;
}
