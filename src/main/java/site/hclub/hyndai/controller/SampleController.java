package site.hclub.hyndai.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.hclub.hyndai.domain.SampleVO;
import site.hclub.hyndai.dto.EmployeeDTO;
import site.hclub.hyndai.service.SampleService;
import site.hclub.hyndai.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sample")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Log4j
public class SampleController {

    private final UserService userService;
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

    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> test(HttpServletRequest request) {


        Map<String, Object> map = new HashMap<>();
        String memberId = userService.getUserDetails(request);

        log.info("resolve" + memberId);
        return ResponseEntity.ok(map);
    }
}
