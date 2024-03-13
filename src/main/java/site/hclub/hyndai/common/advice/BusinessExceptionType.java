package site.hclub.hyndai.common.advice;
import org.springframework.http.HttpStatus;

/**
 * @author 이혜연
 * @description: BusinessExceptionType
 * ===========================
 * AUTHOR      NOTE
 * ---------------------------
 * 이혜연       최초 생성
 * ===========================
 */
public interface BusinessExceptionType {
    HttpStatus status();
    String message();

    default int getHttpStatusCode() {
        return status().value();
    }
}
