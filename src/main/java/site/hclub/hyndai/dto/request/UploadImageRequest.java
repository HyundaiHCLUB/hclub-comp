package site.hclub.hyndai.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UploadImageRequest {
    private Long    imageNo;
    private String  fileName;
    private String  url;
}
