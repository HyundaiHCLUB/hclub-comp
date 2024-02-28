package site.hclub.hyndai.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class GetTeamFromScoreNoResponse {
    private Long    teamNo;
    private String  teamLoc;
    private String  teamName;
    private String  teamGoods;
    private String  matchType;
    private LocalDateTime createdAt;
    private String  teamImage;
    private Long     matchCapacity;
    private Long    teamRating;
    private String  isMatched;
    private String  matchDate;


}
