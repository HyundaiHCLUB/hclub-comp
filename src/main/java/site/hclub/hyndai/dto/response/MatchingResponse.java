package site.hclub.hyndai.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class MatchingResponse {
    private Long teamNo;
    private Long teamMemberNo;
    private String matchType;
    private int matchCapacity;
    private Long teamRating;
}
