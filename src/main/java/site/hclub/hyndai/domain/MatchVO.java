package site.hclub.hyndai.domain;

import lombok.Data;

@Data
public class MatchVO {
    private Long matchHistoryNo;
    private String matchLoc;
    private Long winTeamScoreNo;
    private Long loseTeamScoreNo;
}
