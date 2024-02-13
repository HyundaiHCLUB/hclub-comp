package site.hclub.hyndai.dto;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CreateTeamDTO {

    private String teamName;
    private ArrayList<Long> memberList;
    private String matchType;
    private String teamLoc;
    private String teamGoods;
    private Long matchCapacity;
}
