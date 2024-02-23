package site.hclub.hyndai.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import site.hclub.hyndai.common.advice.BusinessExceptionType;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum UserExceptionType implements BusinessExceptionType {

    NO_TOKEN_EXCEPTION(HttpStatus.BAD_REQUEST, "토큰을 resolve 하는 과정에서 발생"),

    ;

    private final HttpStatus status;
    private final String message;

    @Override
    public HttpStatus status() {
        return this.status;
    }

    @Override
    public String message() {
        return this.message;
    }
}
