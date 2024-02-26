package site.hclub.hyndai.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MatchingRequest {
    private Long teamNo;
    private List<Long> teamMemberNo;
    private String matchType;
    private int matchCapacity;
    private Long teamRating;

    @JsonCreator
    public MatchingRequest(@JsonProperty("teamNo") Long teamNo,
                           @JsonProperty("teamMemberNo") List<Long> teamMemberNo,
                           @JsonProperty("matchType") String matchType,
                           @JsonProperty("matchCapacity") int matchCapacity,
                           @JsonProperty("teamRating") Long teamRating) {
        this.teamNo = teamNo;
        this.teamMemberNo = teamMemberNo;
        this.matchType = matchType;
        this.matchCapacity = matchCapacity;
        this.teamRating = teamRating;
    }
}
