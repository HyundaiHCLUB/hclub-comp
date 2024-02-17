package site.hclub.hyndai.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTO {
    private String teamName;
    private Long teamCapacity;
    private String teamLoc;
    private Long teamRating;
    private String matchType;
    private String matchAt;

}
