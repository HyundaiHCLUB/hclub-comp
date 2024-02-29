package site.hclub.hyndai.dto.response;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoseTeamSettleResponse {
    /* FROM team TABLE (lose team) */
    private Long    productsNo;         // 상품 번호
    private Long    settleMemberId;     // 결제자 id
    private String  settleAmount;       // 결제 금액
    private String  settleName;         // 상품명

}
