package site.hclub.hyndai.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateMatchLocationRequest {
    private Long    matchHistoryNo;
    private String  matchLoc;
}
