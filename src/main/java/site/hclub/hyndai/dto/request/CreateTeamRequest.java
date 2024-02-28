package site.hclub.hyndai.dto.request;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CreateTeamRequest {

    private String teamName;
    private ArrayList<Long> memberList;
    private String matchType;
    private String teamLoc;
    private String teamGoods;
    private Long matchCapacity;
    private String matchDate;
    private Long productNo;
}
