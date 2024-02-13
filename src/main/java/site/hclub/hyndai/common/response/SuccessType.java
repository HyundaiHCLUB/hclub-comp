package site.hclub.hyndai.common.response;
import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessType {
    /**
     * 200 Ok
     */
    GET_MATCH_DETAIL_SUCCESS(HttpStatus.OK, "경기 상세정보 조회 성공")
    /**
     * 201 Created
     */

    ;
    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
