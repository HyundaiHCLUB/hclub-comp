package site.hclub.hyndai.dto.response;

import lombok.Data;

@Data
public class RankResponse {
    private Long    memberNo;
    private Long    rank;
    private Long    memberRating;
    private String  memberId;
    private String  memberImage;

}
