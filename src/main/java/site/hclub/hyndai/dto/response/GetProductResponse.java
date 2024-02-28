package site.hclub.hyndai.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class GetProductResponse {
    private Long productId;
    private String productName;
    private Long productPrice;
}
