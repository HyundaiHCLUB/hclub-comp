package site.hclub.hyndai.common.advice;

import lombok.Data;
import org.springframework.http.HttpStatus;  // 추가

/**
 * @author 이혜연
 * @description: 에러응답클래스
 * ===========================
 * AUTHOR      NOTE
 * ---------------------------
 * 이혜연       최초 생성
 * ===========================
 */
@Data
public class ErrorResponse {
    HttpStatus code;  // HttpStatus로 수정
    String message;
    boolean success;

    public ErrorResponse() {
    }

    public ErrorResponse(HttpStatus code, String message, boolean success) {  // 생성자도 수정
        this.code = code;
        this.message = message;
        this.success = success;
    }

    public static ErrorResponse of(ErrorType errorType) {
        return new ErrorResponse(
                HttpStatus.valueOf(errorType.getHttpStatusCode()),  // 수정
                errorType.getMessage(),
                false
        );
    }

    public static ErrorResponse of(BusinessExceptionType exceptionType) {
        return new ErrorResponse(
                HttpStatus.valueOf(exceptionType.getHttpStatusCode()),  // 수정
                exceptionType.message(),
                false
        );
    }
}
