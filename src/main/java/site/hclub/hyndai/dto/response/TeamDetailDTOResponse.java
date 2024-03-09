package site.hclub.hyndai.dto.response;

import lombok.*;
import site.hclub.hyndai.dto.MemberInfo;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeamDetailDTOResponse {
    private Long teamNo;
    private String teamName;
    private String teamCapacity;
    private String teamLoc;
    private Long teamRating;
    private String matchType;
    private String matchAt;
    private String teamGoods;
    private String teamImage;
    private List<MemberInfo> memberList;
    private String matchKind;

}
