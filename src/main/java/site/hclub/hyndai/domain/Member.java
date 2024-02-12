package site.hclub.hyndai.domain;

import lombok.Data;

@Data
public class Member {
    private Long memberNo;
    private String memberId;
    private String memberImage;
    private String memberInterest;
    private Long memberRating;
    private String memberPw;
    private String adminYn;
}
