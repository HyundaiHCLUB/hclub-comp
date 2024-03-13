package site.hclub.hyndai.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WinTeamSettleResponse {
    private Long      recipentMemberNo;
    private String    settleAmount;
}
