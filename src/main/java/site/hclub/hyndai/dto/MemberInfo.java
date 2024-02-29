package site.hclub.hyndai.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfo {
    private Long memberNo;
    private String memberName;
    private String memberDept;
    private String memberPosition;

}
