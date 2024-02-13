package site.hclub.hyndai.dto;

import lombok.Data;

@Data
public class MatchDetailResponse {
    private long    matchHistoryNo;
    private TeamDetailResponse team1;
    private TeamDetailResponse team2;
}
