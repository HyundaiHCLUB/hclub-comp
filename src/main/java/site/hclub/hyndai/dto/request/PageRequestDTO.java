package site.hclub.hyndai.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageRequestDTO {

    private String gameType;
    private Long players;
    private String date;
    private Long minRating;
    private Long maxRating;
    private String sortOption;
    private String keyword;
    // private Long page;


}
