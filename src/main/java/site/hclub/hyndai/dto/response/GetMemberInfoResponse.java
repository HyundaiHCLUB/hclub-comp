package site.hclub.hyndai.dto.response;

import lombok.*;
import site.hclub.hyndai.dto.MemberInfo;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetMemberInfoResponse {
    List<MemberInfo> memberList;
}
