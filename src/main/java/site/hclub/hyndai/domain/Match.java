package site.hclub.hyndai.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Match {
    private Long matchHistoryNo;
    private String matchLoc;
    private Long winTeamScoreNo;
    private Long loseTeamScoreNo;
    private LocalDateTime matchDate;
}
