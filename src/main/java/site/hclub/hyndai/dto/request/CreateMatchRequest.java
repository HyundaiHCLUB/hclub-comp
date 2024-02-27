package site.hclub.hyndai.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateMatchRequest {
    private Long    team1No;
    private Long    team2No;
    private String  matchLoc;
}
