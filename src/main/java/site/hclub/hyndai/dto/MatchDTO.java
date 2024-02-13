package site.hclub.hyndai.dto;

import lombok.Data;
import site.hclub.hyndai.domain.Team;

@Data
public class MatchDTO {
    private long matchHistoryNo;
    private Team team1;
    private Team team2;
}
