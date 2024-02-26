package site.hclub.hyndai.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import site.hclub.hyndai.common.response.ApiResponse;
import site.hclub.hyndai.dto.SettleDTO;
import site.hclub.hyndai.dto.TeamDTO;
import site.hclub.hyndai.dto.request.*;
import site.hclub.hyndai.dto.response.*;
import site.hclub.hyndai.service.CompService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static site.hclub.hyndai.common.advice.ErrorType.MATCH_NOT_FOUND_ERROR;
import static site.hclub.hyndai.common.response.SuccessType.*;


@Log4j
@RestController
@RequestMapping("/comp")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)

public class CompController {


    private final CompService compService;

    // 경쟁 메인 페이지로 이동
    @GetMapping("/main")
    public ModelAndView goCompMain() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("comp/compMain");
        return mav;
    }

    /* 매칭 상세페이지로 이동 */
    @GetMapping("/matchDetail")
    public ModelAndView goMatchDetailPage() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("comp/MatchDetail");
        return mav;
    }

    /*
     * @작성자 : 송원선
     * 경기 상세 정보 조회 API
     * @request  : 경기번호 (match_hist_no)
     * @response : 경기 정보 (MatchDetailRespnse -> 안에 Team1 Team2 정보 담겨있음)
     *
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
        return ApiResponse.success(CREATE_TEAM_SUCCESS, compService.makeTeam(teamDTO, multipartFile));
    }

    @GetMapping("/{teamNo}")
    public ResponseEntity<ApiResponse<TeamDTO>> getTeamInfo(@PathVariable(value = "teamNo") Long teamNo) {
        log.info("getTeamInfo=======>");


        return ApiResponse.success(GET_TEAM_DETAIL_SUCCESS, compService.getTeamDetail(teamNo));
    }

    @GetMapping("/member")
    public ResponseEntity<ApiResponse<GetMemberInfoResponse>> getMemberInfo(@RequestParam(value = "memberName") String memberName) {
        log.info("getMemberInfo=======>");


        return ApiResponse.success(GET_MEMBER_DETAIL_SUCCESS, compService.getMemberInfo(memberName));
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<TeamDTO>>> getTeamList(@RequestParam(value = "gameType", required = false, defaultValue = "ALL") String gameType,
                                                                  @RequestParam(value = "players", required = false, defaultValue = "0") Long players,
                                                                  @RequestParam(value = "date", required = false, defaultValue = "ALL") String date,
                                                                  @RequestParam(value = "minRating", required = false, defaultValue = "0") Long minRating,
                                                                  @RequestParam(value = "maxRating", required = false, defaultValue = "30000") Long maxRating,
                                                                  @RequestParam(value = "sortOption", required = false, defaultValue = "dates") String sortOption,
                                                                  @RequestParam(value = "keyword", required = false, defaultValue = "NONE") String keyword) {
        PageRequestDTO pageRequestDTO = new PageRequestDTO(gameType, players, date, minRating, maxRating, sortOption, keyword);

        log.info("getTeamList=======>");


        return ApiResponse.success(GET_TEAM_LIST_SUCCESS, compService.getTeamList(pageRequestDTO));
    }

    /*
     * @작성자 : 송원선
     * 경기 결과 기록 API
     * @request : RequestBody
     *
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
            // 3. 경기 일자 등록
            compService.updateMatchDate(request.getMatchDate(), request.getMatchHistNo());
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

    // 상위 num 명의 랭크 (레이팅 순)
    @GetMapping(value = "/rank", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<RankResponse>>> getRankingList(@RequestParam("num") int num) {

        List<RankResponse> list = compService.getRankList(num);
        log.info("Top 10 ranking ==> " + list.toString());
        return ApiResponse.success(GET_RANK_LIST_SUCCESS, list);
    }

    @RequestMapping(value = "/chatPage")
    public ModelAndView getChatViewPage(ModelAndView mav) {
        mav.setViewName("chatPage");
        return mav;
    }

    @RequestMapping(value = "/chatPage2")
    public ModelAndView getChatViewPage2(ModelAndView mav) {
        mav.setViewName("chatPage2");
        return mav;
    }

    @RequestMapping(value = "/test")
    public ModelAndView getTest(ModelAndView mav) {
        System.out.println("show page");
        mav.setViewName("testPage");
        return mav;
    }



    /**
     * 팀 매칭 API
     * (웹 소켓 or 레디스) -> 2개의 팀 정보 꺼내면 -> match 테이블에 추가
     * + 두 팀에 같은 사람 있으면 매칭 안되게 하는 로직 추가
     * @reqyest (1) 두 팀의 번호(team_no)
     * (2) 경기 장소(match_loc) : 두 팀의 team_loc 중 하나
     */
    @PostMapping("/match")
    public ResponseEntity<ApiResponse<Void>> createMatch(@RequestBody CreateMatchRequest request) {

        log.info("[POST] /comp/match : 경기 생성 ===> ");
        log.info("TEAM 1 NO : " + request.getTeam1No() + " , TEAM 2 NO : " + request.getTeam2No());
        try {
            // 1. 중복 팀원 검사
            List<Long> memberList1 = compService.getTeamMemberList(request.getTeam1No()); // 1팀 명단
            List<Long> memberList2 = compService.getTeamMemberList(request.getTeam2No()); // 2팀 명단
            // memberList1을 복사하여 중복 검사를 수행
            List<Long> duplicates = new ArrayList<>(memberList2);
            // duplicates 리스트에 memberList2와 겹치는 원소만 남기기
            boolean hasDuplicates = duplicates.retainAll(memberList2); // 겹치는 원소 있다면 true
            if (hasDuplicates) {
                /**
                 *  중복된 팀원 있는 경우
                 *  => 매칭 취소 로직 구현 필요 (예 : 레디스 큐에서 꺼내지 않는다)
                 * */
                log.info("두 팀에 중복된 팀원이 있습니다.");
                log.info("중복된 팀원의 ID: " + duplicates);
            } else {
                // 중복된 팀원 없음 => 정상적으로 매치 진행
                // 2. match 테이블에 추가
                compService.generateMatch(request);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return ApiResponse.success(MATCH_CREATED);
    }

    @PostMapping("/settle")
    public ResponseEntity<ApiResponse<Void>> insertSettle(@RequestBody SettleDTO sdto) {

        compService.insertSettle(sdto);

        return ApiResponse.success(INSERT_SETTLE_SUCCESS);
    }

    /* 경기 기록 페이지로 이동*/
    @GetMapping("/matchRecord")
    public ModelAndView goMatchRecordPage() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("comp/matchRecord");
        return mav;
    }

    /* 메인페이지 - 오늘의 TOP10 랭킹 페이지로 이동 */
    @GetMapping("/todayRanking")
    public ModelAndView goTodayRanking() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("comp/todayRanking");
        return mav;
    }

    /****** 마이페이지 뷰 이동 ******/
    /* 마이페이지로 이동 */
    @GetMapping("/mypage")
    public ModelAndView goMyPage(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("comp/mypageMain");
        return mav;
    }
    /* 회원정보 수정페이지로 이동*/
    @GetMapping("/updateProfileView")
    public ModelAndView goUpdateProfileView(){
        return new ModelAndView("comp/updateProfile");
    }


    // 경기 상세 정보 페이지 -> 경기 기록 페이지 넘어갈 때 경기 장소(matchLoc) DB에 저장하는 API
    @PostMapping(value= "matchLocation", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<UpdateMatchLocationRequest>> setMatchLocation(@RequestBody UpdateMatchLocationRequest request){
        log.info("request.matchLoc -> " + request.toString());
        try {
            compService.updateMatchLocation(request);
        }catch (Exception e) {
            log.error(e);
        }
        return ApiResponse.success(UPDATE_MATCH_LOC_SUCCESS);
    }
}