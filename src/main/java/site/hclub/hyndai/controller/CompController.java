package site.hclub.hyndai.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import site.hclub.hyndai.common.response.ApiResponse;
import site.hclub.hyndai.dto.request.AfterMatchRatingRequest;
import site.hclub.hyndai.dto.request.CompHistoryRequest;
import site.hclub.hyndai.dto.request.CreateTeamRequest;
import site.hclub.hyndai.dto.request.HistoryModifyRequest;
import site.hclub.hyndai.dto.response.*;
import site.hclub.hyndai.dto.response.HistoryDetailResponse;
import site.hclub.hyndai.dto.response.MatchDetailResponse;
import site.hclub.hyndai.dto.*;
import site.hclub.hyndai.dto.response.RankResponse;
import site.hclub.hyndai.service.CompService;

import java.io.IOException;
import java.util.List;

import static site.hclub.hyndai.common.advice.ErrorType.MATCH_NOT_FOUND_ERROR;
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
     * @response : 경기 정보 (MatchDetailRespnse -> 안에 Team1 Team2 정보 담겨있음)
     * - 현재 createdAt (경기 생성 일자) null 로 반환
     */
    @GetMapping("/match/{match_hist_no}")
    public ResponseEntity<ApiResponse<MatchDetailResponse>> getMatchDetail(@PathVariable("match_hist_no") Long matchHistoryNo) {
        MatchDetailResponse response = compService.getMatchDetail(matchHistoryNo);
        log.info("Match Info =======> " + response.toString());
        if (response.getTeam1() == null || response.getTeam2() == null) {
            return ApiResponse.fail(MATCH_NOT_FOUND_ERROR, response);
        }
        return ApiResponse.success(GET_MATCH_DETAIL_SUCCESS, response);
    }


    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<CreateTeamResponse>> makeTeam(@RequestPart(value = "teamDTO") CreateTeamRequest teamDTO,
                                                                    @RequestPart(value = "multipartFile") MultipartFile multipartFile) throws IOException {
        log.info("createTeam=======>");
        log.info(teamDTO.toString());
        log.info(multipartFile);
        compService.makeTeam(teamDTO, multipartFile);
        return ApiResponse.success(CREATE_TEAM_SUCCESS, compService.makeTeam(teamDTO, multipartFile));
    }

    @GetMapping("/{teamNo}")
    public ResponseEntity<ApiResponse<GetTeamDetailResponse>> getTeamInfo(@PathVariable(value = "teamNo") Long teamNo) {
        log.info("getTeamInfo=======>");


        return ApiResponse.success(GET_TEAM_DETAIL_SUCCESS, compService.getTeamDetail(teamNo));
    }

    @GetMapping("/member")
    public ResponseEntity<ApiResponse<GetMemberInfoResponse>> getMemberInfo(@RequestParam(value = "memberName") String memberName) {
        log.info("getMemberInfo=======>");


        return ApiResponse.success(GET_MEMBER_DETAIL_SUCCESS, compService.getMemberInfo(memberName));
    }

    /*
     * @작성자 : 송원선
     * 경기 결과 기록 API
     * @request : RequestBody
     * @response : ?
     */
    @PostMapping(value = "/history",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponse<Void>> recordMatchHistory(@RequestPart(value = "historyRequest") CompHistoryRequest request,
                                                                @RequestPart(value = "multipartFile") MultipartFile multipartFile) {
        log.info("record History ====>");
        try {
            // 1. 경기 스코어 등록
            Long matchHistNo = request.getMatchHistNo();
            compService.updateScore(request.getTeamANo(), request.getScoreA());
            compService.updateScore(request.getTeamBNo(), request.getScoreB());
            // 2. 경기 사진 등록
            compService.uploadHistoryImage(multipartFile);
        } catch (IOException e) {
            log.error("사진 업로드 실패 : " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return ApiResponse.success(UPDATE_HISTORY_RECORD_SUCCESS);
    }


    /*
    경기 기록 조회 API
    @request : 경기 번호
    @ response : MatchDetailResponse
     */
    @GetMapping(value = "/history/{match_hist_no}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<HistoryDetailResponse>> getMatchHistoryDetail(@PathVariable("match_hist_no") Long matchHistNo) {

        log.info("getMatchHistoryDetail ====> ");
        HistoryDetailResponse response = compService.getHistoryDetail(matchHistNo);
        return ApiResponse.success(GET_HISTORY_DETAIL_SUCCESS, response);
    }

    /*
     * 경기 기록 수정 API -> 변경 가능 정보 : 점수 only
     * @request : 이긴 팀 번호&점수, 진 팀 번호&점수
     * */
    @PatchMapping(value = "/history",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Void>> modifyMatchHistory(@RequestBody HistoryModifyRequest request) {
        log.info("modifyMatchHistory ====> ");
        log.info("winTeam >> scoreNo : " + request.getWinTeamScoreNo() + " | scoreAmount : " + request.getWinTeamScoreAmount());
        log.info("loseTeam >> scoreNo : " + request.getLoseTeamScoreNo() + " | scoreAmount : " + request.getLoseTeamScoreAmount());
        compService.modifyMatchHistory(request);
        return ApiResponse.success(UPDATE_HISTORY_RECORD_SUCCESS);
    }

    /*
     * 레이팅 반영 API (경기 종료후 결과 입력 -> Elo 알고리즘에 따라 개인 레이팅 변경)
     * @request : AfterMatchRatingRequest
     * - Long matchHistNo : 경기 번호
     * - winTeamNo / winTeamScore : 이긴 팀 번호 및 점수
     * - loseTeamNo / loseTeamScore : 진 팀 번호 및 점수
     * */
    @PostMapping(value = "/rating",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<Long>>> updateRating(@RequestBody AfterMatchRatingRequest request) {
        log.info("updateRating ====> ");
        // 레이팅 변경하고 변동값을 리턴
        List<Long> ratingChange = compService.updateRating(request);
        log.info("레이팅 변동 : " + ratingChange);
        return ApiResponse.success(UPDATE_RATING_SUCCESS, ratingChange);
    }

    // 상위 10명의 랭크
    @GetMapping(value = "/rank", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<RankResponse>>> getRankingList() {

        List<RankResponse> list = compService.getRankList();
        log.info("Top 10 ranking ==> " + list.toString());
        return ApiResponse.success(GET_RANK_LIST_SUCCESS, list);
    }
}
