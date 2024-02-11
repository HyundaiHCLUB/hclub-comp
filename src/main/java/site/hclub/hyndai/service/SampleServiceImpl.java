package site.hclub.hyndai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.hclub.hyndai.domain.SampleVO;
import site.hclub.hyndai.dto.EmployeeDTO;
import site.hclub.hyndai.mapper.SampleMapper;

import java.util.List;


@Service
public class SampleServiceImpl implements SampleService {

    @Autowired
    SampleMapper sampleMapper;


    @Override
    public List<SampleVO> getSampleList(SampleVO svo) {

        return sampleMapper.getSampleList(svo);
    }

    @Override
    public List<EmployeeDTO> getEmployeeList() {


        return sampleMapper.getEmployeeList();
    }

}
