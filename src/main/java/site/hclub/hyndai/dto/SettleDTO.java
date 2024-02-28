package site.hclub.hyndai.dto;

import lombok.Data;

/**
 * @author 김은솔
 * @description: 결제 관련 DTO를 작성한다.
 * ===========================
	   AUTHOR      NOTE
 * ---------------------------
 *     김은솔       최초생성   
 * ===========================
 */
@Data
public class SettleDTO {

	/**
	 결제 번호
	 */
	private long settleNo;
	/**
	 경기 기록 번호
	 */
	private long matchHistNo;
	/**
	 총 결제 금액
	 */
	private String settleAmount;
	/**
	 결제 대상  : 결제 상품 이름
	 */
	private String settleName;
	/**
	 상품번호
	 */
	private String productsNo;
	/**
	 결제자: 결제한 사람 멤버 아이디
	 */
	private String settleMemberId;
	/**
	 받는 사람 번호
	 */
	private String recipentMemberNo;
}
