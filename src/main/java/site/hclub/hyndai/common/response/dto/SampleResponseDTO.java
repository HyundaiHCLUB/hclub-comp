package site.hclub.hyndai.common.respone.dto;

import lombok.Data;
import site.hclub.hyndai.domain.SampleVO;

import java.util.List;


@Data
public class SampleResponseDTO {
    private List<SampleVO> sampleList;
}
