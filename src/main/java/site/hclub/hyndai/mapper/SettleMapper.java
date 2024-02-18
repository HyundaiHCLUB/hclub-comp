package site.hclub.hyndai.mapper;

import site.hclub.hyndai.dto.SettleDTO;

/**
 * @author 김은솔
 * @description: 결제 관련 Mapper 인터페이스를 작성한다.
 * ===========================
	   AUTHOR      NOTE
 * ---------------------------
 *    김은솔         최초생성
 * ===========================
 */
public interface SettleMapper {

	/**
	 작성자: 김은솔 
	 처리 내용: 결제 정보를 삽입한다.
	*/
	int insertSettle(SettleDTO sdto);

}
