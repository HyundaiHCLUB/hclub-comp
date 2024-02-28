package site.hclub.hyndai.dto.response;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoseTeamSettleResponse {
    /* FROM settlement TABLE */
    private Long    productsNo;         // 상품 번호
    private Long    settleMemberId;     // 결제자 id
    private Long    recipentMemberNo;   // 받는사람 번호
    private String  settleAmount;       // 결제 금액

    /* FROM products TABLE */
    private String  settleName;         // 상품명

}
