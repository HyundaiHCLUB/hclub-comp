package site.hclub.hyndai.controller;

import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import site.hclub.hyndai.common.response.ApiResponse;
import site.hclub.hyndai.dto.CompHistoryRequest;
import site.hclub.hyndai.dto.CreateTeamDTO;
import site.hclub.hyndai.dto.MatchDetailResponse;
import site.hclub.hyndai.service.CompService;
import java.io.IOException;
import static site.hclub.hyndai.common.response.SuccessType.CREATE_TEAM_SUCCESS;
import static site.hclub.hyndai.common.response.SuccessType.GET_MATCH_DETAIL_SUCCESS;
import static site.hclub.hyndai.common.response.SuccessType.*;


@RestController
@RequestMapping("/comp")
@Log4j
public class CompController {

    @Autowired
    CompService compService;

    /* 매칭 상세페이지로 이동 */
    @GetMapping("/matchDetail")
    public ModelAndView goMatchDetailPage() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("/MatchDetail");
        return mav;
    }

    /*
    * @작성자 : 송원선
    * 경기 상세 정보 조회 API
    * @request  : 경기번호 (match_hist_no)
    * @response : 경기 정보 (MatchDTO -> 안에 Team1 Team2 정보 담겨있음)
    *
    */

    @GetMapping("/match/{match_hist_no}")
    public ResponseEntity<ApiResponse<MatchDetailResponse>> getMatchDetail(@PathVariable("match_hist_no") Long matchHistoryNo) {
        MatchDetailResponse response = compService.getMatchDetail(matchHistoryNo);
        log.info("Match Info =======> " + response.toString());
        return ApiResponse.success(GET_MATCH_DETAIL_SUCCESS, response);
    }


    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<CreateTeamDTO>> makeTeam(@RequestPart(value = "teamDTO") CreateTeamDTO teamDTO,
                                                               @RequestPart(value = "multipartFile") MultipartFile multipartFile) throws IOException {
        log.info("createTeam=======>");
        compService.makeTeam(teamDTO, multipartFile);
        return ApiResponse.success(CREATE_TEAM_SUCCESS);
    }

    /*
    * @작성자 : 송원선
    * 경기 결과 기록 API
    * @request : RequestBody
    * @response : ?
    */
    @PostMapping(value = "/history",
                consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponse<Void>> recordMatchHistory(@RequestBody CompHistoryRequest request){
        /*
        1. 경기 스코어 등록
        (1) 두 팀 점수를 score 테이블에 반영 (디폴트값 0 -> 수정)
        (2) match 테이블에 [위에서 생성한 점수] 번호 + 장소 저장
        */
        Long matchHistNo = request.getMatchHistNo();
        // (1)
        compService.updateScore(request.getTeamANo(), request.getScoreA());
        compService.updateScore(request.getTeamBNo(), request.getScoreB());
        // 아직 여기 작업중
        // 2. 경기 사진 등록

        return ApiResponse.success(UPDATE_HISTORY_RECORD_SUCCESS);
    }
}
