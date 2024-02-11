package site.hclub.hyndai.common.advice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.hclub.hyndai.common.response.ApiResponse;

import javax.servlet.http.HttpServletRequest;

import static site.hclub.hyndai.common.advice.CommonExceptionType.*;
import static site.hclub.hyndai.common.advice.ErrorType.INDEX_OUT_OF_BOUND_ERROR;
import static site.hclub.hyndai.common.advice.ErrorType.INTERNAL_SERVER_ERROR;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 400 Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResponse<Map<String, String>>> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {

        Errors errors = e.getBindingResult();
        Map<String, String> validateDetails = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validateDetails.put(validKeyName, error.getDefaultMessage());
        }
        return ApiResponse.fail(REQUEST_VALIDATION_EXCEPTION, validateDetails);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(final HttpMessageNotReadableException e) {
        log.error("GlobalExceptionHandler에서 잡은 Exception: {}", e.getMessage());
        ErrorResponse ex = ErrorResponse.of(VALIDATION_WRONG_HTTP_REQUEST);
        return ResponseEntity.status(ex.getCode().value()).body(ex);
    }


    /**
     * 405 Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handlerHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e) {
        log.error("GlobalExceptionHandler에서 잡은 Exception: {}", e.getMessage());
        ErrorResponse ex = ErrorResponse.of(INVALID_HTTP_METHOD);
        return ResponseEntity.status(ex.getCode().value()).body(ex);
    }

    /**
     * 500 Internal Server Error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(final Exception e, final HttpServletRequest request) throws IOException {

        log.error("GlobalExceptionHandler에서 잡은 Exception: {}", e.getMessage());
        ErrorResponse ex = ErrorResponse.of(INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(ex.getCode().value()).body(ex);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResponseEntity<ErrorResponse> handleIndexOutOfBoundsException(final IndexOutOfBoundsException e) {
        log.error("GlobalExceptionHandler에서 잡은 Exception: {}", e.getMessage());
        ErrorResponse ex = ErrorResponse.of(INDEX_OUT_OF_BOUND_ERROR);
        return ResponseEntity.status(ex.getCode().value()).body(ex);
    }



    /**
     * Custom Error
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException e) {

        log.error("🚨🚨🚨 BusinessException occured: {} 🚨🚨🚨", e.getMessage());

        return ResponseEntity.status(e.getHttpStatus())
                .body(ErrorResponse.of(e.getExceptionType()));
    }

}
