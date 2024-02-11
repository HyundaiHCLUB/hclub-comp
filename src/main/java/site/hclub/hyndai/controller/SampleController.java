package site.hclub.hyndai.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.hclub.hyndai.domain.SampleVO;
import site.hclub.hyndai.dto.EmployeeDTO;
import site.hclub.hyndai.service.SampleService;

import java.util.List;

@RestController
@RequestMapping("/sample")
@Log4j
public class SampleController {

    @Autowired
    SampleService sampleService;

    @RequestMapping("/list")
    public ResponseEntity<List<SampleVO>> getSampleList(SampleVO svo) {

        return ResponseEntity.ok(sampleService.getSampleList(svo));
    }

    @GetMapping("/")
    public ResponseEntity<List<EmployeeDTO>> getEmployeeList() {
        return ResponseEntity.ok(sampleService.getEmployeeList());
    }
}
