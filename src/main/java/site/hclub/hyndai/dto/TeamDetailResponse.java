package site.hclub.hyndai.dto;

import lombok.Data;
import site.hclub.hyndai.domain.Member;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TeamDetailResponse {
    private Long        teamNo;
    private String      teamLoc;
    private String      teamName;
    private String      teamGoods;
    private LocalDateTime createdAt;
    private String      matchType;
    private Long        matchCapacity;
    private Member      leader;
    private List<Member> members;
    private int         teamRating;
}
