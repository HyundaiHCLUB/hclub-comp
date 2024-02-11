package site.hclub.hyndai.common.util;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import site.hclub.hyndai.common.base.dto.ErrorContentDTO;
import site.hclub.hyndai.common.base.dto.ResponseErrorDTO;
import site.hclub.hyndai.common.base.dto.ResponseSuccessDTO;


@Component
@Slf4j
public class ResponseUtil<T> {
	
	@Autowired
	private TimeService timeService;
	  public ResponseSuccessDTO<T> successResponse(T result, HttpStatus status) {
		  ResponseSuccessDTO<T> res = ResponseSuccessDTO
	                .<T>builder()
	                .timeStamp(timeService.parseLocalDateTime(LocalDateTime.now()))
	                .code(status.value())
	                .status(status)
	                .result(result)
	                .build();
		
	        return res;
	    }

	    public ResponseErrorDTO<ErrorContentDTO> buildErrorResponse(HttpStatus status, String message, String path) {
	        ErrorContentDTO errorContentDto = ErrorContentDTO.builder()
	                .message(message)
	                .build();

	        return ResponseErrorDTO
	                .<ErrorContentDTO>builder()
	                .timeStamp(timeService.parseLocalDateTime(LocalDateTime.now()))
	                .code(status.value())
	                .status(status)
	                .path(path)
	                .error(errorContentDto)
	                .build();
	    }

}
