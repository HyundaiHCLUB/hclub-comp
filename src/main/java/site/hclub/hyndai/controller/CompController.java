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
    /*
    *  To. 동욱
    *  CompServiceImpl 에 주입할 mapper 파일은 아직 생성 안했습니다
    *  <= resources > mapper > ...  폴더 구조 헷갈림 이슈
    * (mapper/site.hclub/hyndai/ 에 작성 하는건지 mapper/ 에 작성인지 하는건지?)
    * */

    /* 매칭 상세페이지로 이동*/
    @GetMapping("/matchDetail")
    public ModelAndView goMatchDetailPage(){
        ModelAndView mav = new ModelAndView();

        mav.setViewName("/MatchDetail");
        return mav;
    }
}
