package site.hclub.hyndai.common.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessType {
    /**
     * 200 Ok
     */
    GET_MATCH_DETAIL_SUCCESS(HttpStatus.OK, "경기 상세정보 조회 성공"),
    

    CREATE_TEAM_SUCCESS(HttpStatus.OK, "팀 생성 성공"),
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
