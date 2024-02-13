package site.hclub.hyndai.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import site.hclub.hyndai.common.response.ApiResponse;
import site.hclub.hyndai.domain.MatchVO;
import site.hclub.hyndai.dto.MatchDTO;
import site.hclub.hyndai.service.CompService;

import static site.hclub.hyndai.common.response.SuccessType.GET_MATCH_DETAIL_SUCCESS;

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

    /*
    * 경기 상세 정보 조회
    * @param  : 경기번호 (match_hist_no)
    * @response : 경기 정보 (MatchDTO -> 안에 Team1 Team2 정보 담겨있음)
    *
    *  */
    @GetMapping("/match/{match_hist_no}")
    public ResponseEntity<ApiResponse<MatchDTO>> getMatchDetail(@PathVariable long matchHistoryNo){
        MatchDTO dto = compService.getMatchDetail(matchHistoryNo);

        return ApiResponse.success(GET_MATCH_DETAIL_SUCCESS, dto);
    }
}
