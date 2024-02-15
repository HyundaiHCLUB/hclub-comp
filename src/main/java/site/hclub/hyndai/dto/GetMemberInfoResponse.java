package site.hclub.hyndai.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetMemberInfoResponse {
    List<MemberInfo> memberList;
}
