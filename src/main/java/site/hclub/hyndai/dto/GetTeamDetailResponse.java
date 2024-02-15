package site.hclub.hyndai.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetTeamDetailResponse {
    private Long teamNo;
    private String teamName;
    private String matchType;
    private String teamLoc;
    private String teamGoods;
    private Long matchCapacity;
    private Long teamRating;
}
