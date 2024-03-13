package site.hclub.hyndai.common.advice;
import lombok.Getter;

/**
 * @author 이혜연
 * @description: BusinessException
 * ===========================
 * AUTHOR      NOTE
 * ---------------------------
 * 이혜연       최초 생성
 * ===========================
 */
@Getter
public class BusinessException extends RuntimeException {

    private final BusinessExceptionType exceptionType;

    public BusinessException(BusinessExceptionType exceptionType) {
        super(exceptionType.message());
        this.exceptionType = exceptionType;
    }

    public BusinessException(String message, BusinessExceptionType exceptionType) {
        super(message);
        this.exceptionType = exceptionType;
    }

    public int getHttpStatus() {
        return exceptionType.status().value();
    }
}