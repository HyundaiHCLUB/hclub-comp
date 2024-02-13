package site.hclub.hyndai.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberTeam {
    private Long memberTeamNo;
    private Long memberNo;
    private Long teamNo;
    private String isLeader;
}
