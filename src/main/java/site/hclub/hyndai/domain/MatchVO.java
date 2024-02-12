package site.hclub.hyndai.domain;

import lombok.Data;

@Data
public class MatchVO {
    private long matchHistoryNo;
    private String matchLoc;
    private long winTeamScoreNo;
    private long loseTeamScoreNo;
}
