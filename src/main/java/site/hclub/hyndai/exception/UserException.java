package site.hclub.hyndai.exception;

import site.hclub.hyndai.common.advice.BusinessException;
import site.hclub.hyndai.common.advice.BusinessExceptionType;

public class UserException extends BusinessException {
    public UserException(BusinessExceptionType exceptionType) {
        super(exceptionType);
    }
}
