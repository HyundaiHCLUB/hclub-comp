package site.hclub.hyndai.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Team {

    private Long teamNo;
    private String teamLoc;
    private String teamName;
    private String teamGoods;
    private LocalDateTime createdAt;
    private String matchType;
    private Long matchCapacity;
}
