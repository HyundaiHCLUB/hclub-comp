package site.hclub.hyndai.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Team {

    private Long teamNo;
    private String teamLoc;
    private String teamName;
    private String teamGoods;
    private LocalDateTime createdAt;
    private String matchType;
    private String teamImage;
    private Long matchCapacity;
    private Long teamRating;
    private String isMatched;
    private LocalDateTime matchDate;
    private Long productsNo;
}
