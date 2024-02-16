package site.hclub.hyndai.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CreateTeamResponse {


    private Long teamNo;
    private String teamName;
    private String matchType;
    private String teamLoc;
    private String teamGoods;
    private Long matchCapacity;
}
