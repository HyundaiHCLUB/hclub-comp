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
    UPDATE_HISTORY_RECORD_SUCCESS(HttpStatus.OK, "경기 결과 기록 성공"),
    GET_HISTORY_DETAIL_SUCCESS(HttpStatus.OK, "경기 기록 조회 성공"),
    UPDATE_RATING_SUCCESS(HttpStatus.OK, "레이팅 변경 성공"),
    GET_TEAM_DETAIL_SUCCESS(HttpStatus.OK, "팀 상세보기 성공"),
    GET_MEMBER_DETAIL_SUCCESS(HttpStatus.OK, "멤버 리스트 가져오기 성공"),
    GET_TEAM_LIST_SUCCESS(HttpStatus.OK, "팀 리스트 가져오기 성공"),
    GET_RANK_LIST_SUCCESS(HttpStatus.OK, "랭킹 리스트 조회 성공"),
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
