package site.hclub.hyndai.mapper;

import site.hclub.hyndai.domain.SampleVO;
import site.hclub.hyndai.dto.EmployeeDTO;

import java.util.List;

public interface SampleMapper {

    List<SampleVO> getSampleList(SampleVO svo);

    List<EmployeeDTO> getEmployeeList();
}
