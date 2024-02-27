package site.hclub.hyndai.common.response.dto;

import lombok.Data;
import site.hclub.hyndai.domain.SampleVO;

import java.util.List;


@Data
public class SampleResponseDTO {
    private List<SampleVO> sampleList;
}
