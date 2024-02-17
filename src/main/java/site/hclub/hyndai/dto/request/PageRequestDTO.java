package site.hclub.hyndai.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {
    private Long page;
    private String sortBy;
    private int inOrder;

}
