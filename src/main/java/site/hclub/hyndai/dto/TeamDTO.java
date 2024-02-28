package site.hclub.hyndai.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeamDTO {
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

}
