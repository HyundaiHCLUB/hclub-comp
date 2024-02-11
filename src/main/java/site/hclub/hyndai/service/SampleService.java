package site.hclub.hyndai.service;

import site.hclub.hyndai.domain.SampleVO;
import site.hclub.hyndai.dto.EmployeeDTO;

import java.util.List;

public interface SampleService {

    List<SampleVO> getSampleList(SampleVO svo);

    List<EmployeeDTO> getEmployeeList();
}
