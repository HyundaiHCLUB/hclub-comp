package site.hclub.hyndai.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import site.hclub.hyndai.service.CompService;

@RestController
@RequestMapping("/comp")
@Log4j
public class CompController {

    @Autowired
    CompService compService;

    /* 매칭 상세페이지로 이동*/
    @GetMapping("/matchDetail")
    public ModelAndView goMatchDetailPage(){
        ModelAndView mav = new ModelAndView();

        mav.setViewName("/MatchDetail");
        return mav;
    }
}
