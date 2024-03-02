package site.hclub.hyndai.dto.response;

import lombok.Data;

@Data
public class CreateMatchResponse {
    private Long    scoreNo1;
    private Long    scoreNo2;
    private String  matchLoc;
    private Long    matchHistoryNo;
}
