package site.hclub.hyndai.dto.response;

import lombok.*;
import site.hclub.hyndai.dto.MemberInfo;

import java.util.List;

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
    private List<MemberInfo> memberList;
    private Long teamRating;
}
