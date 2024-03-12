package site.hclub.hyndai.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
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
import site.hclub.hyndai.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import static site.hclub.hyndai.common.advice.ErrorType.MATCH_NOT_FOUND_ERROR;
import static site.hclub.hyndai.common.response.SuccessType.*;

//@CrossOrigin(origins = "http://localhost:8080")
@Log4j
@RestController
@RequestMapping("/comp")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)

public class CompController {


    private final CompService compService;
    private final UserService userService;

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

    /**
     @author: 김동욱
     @description: 경쟁 - 팀을 생성
     @request : teamDTO
     @response : team 정보
     */
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<CreateTeamResponse>> makeTeam(@RequestPart(value = "teamDTO") CreateTeamRequest teamDTO,
                                                                    @RequestPart(value = "multipartFile") MultipartFile multipartFile) throws IOException {

        return ApiResponse.success(CREATE_TEAM_SUCCESS, compService.makeTeam(teamDTO, multipartFile));
    }
    /**
     @author: 김동욱
     @description: 경쟁 - 팀 상세보기
     @request : teamNo
     @response : team 정보
     */
    @GetMapping("/{teamNo}")
    public ResponseEntity<ApiResponse<TeamDetailDTOResponse>> getTeamInfo(@PathVariable(value = "teamNo") Long teamNo) {

        return ApiResponse.success(GET_TEAM_DETAIL_SUCCESS, compService.getTeamDetail(teamNo));
    }
    /**
     @author: 김동욱
     @description: 경쟁 - 멤버 검색하기
     @request : memberName(멤버 이름)
     @response : MemberDTO
     */
    @GetMapping("/member")
    public ResponseEntity<ApiResponse<GetMemberInfoResponse>> getMemberInfo(@RequestParam(value = "memberName") String memberName) {
        log.info("getMemberInfo=======>");


        return ApiResponse.success(GET_MEMBER_DETAIL_SUCCESS, compService.getMemberInfo(memberName));
    }
    /**
     @author: 김동욱
     @description: 경쟁 - 팀 리스트 조회하기
     @request : (Optional) 게임 종류, 참여 인원, 날짜, 최소 레이팅, 최대 레이팅, 정렬 기준, 검색 키워드
     @response : teamListDTO
     */
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<TeamDTO>>> getTeamList(@RequestParam(value = "gameType", required = false, defaultValue = "ALL") String gameType,
                                                                  @RequestParam(value = "players", required = false, defaultValue = "0") Long players,
                                                                  @RequestParam(value = "date", required = false, defaultValue = "ALL") String date,
                                                                  @RequestParam(value = "minRating", required = false, defaultValue = "0") Long minRating,
                                                                  @RequestParam(value = "maxRating", required = false, defaultValue = "30000") Long maxRating,
                                                                  @RequestParam(value = "sortOption", required = false, defaultValue = "dates") String sortOption,
                                                                  @RequestParam(value = "keyword", required = false, defaultValue = "NONE") String keyword) {
        PageRequestDTO pageRequestDTO = new PageRequestDTO(gameType, players, date, minRating, maxRating, sortOption, keyword);




        return ApiResponse.success(GET_TEAM_LIST_SUCCESS, compService.getTeamList(pageRequestDTO));
    }

    /**
     * @작성자 : 송원선
     * 경기 결과 기록 API
     * @request : RequestPart (MultipartFile)
     * @request : RequestPart (CompHistoryRequest)
     */
    @PostMapping(value = "/history",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponse<Void>> recordMatchHistory(@RequestPart(value = "historyRequest") CompHistoryRequest request,
                                                                @RequestPart(value = "multipartFile") MultipartFile multipartFile) {
        log.info("record History ====>");
        try {
            // 1. 경기 스코어 등록
            Long matchHistNo = request.getMatchHistNo();
            compService.updateScore(matchHistNo, request.getTeamANo(), request.getScoreA());
            compService.updateScore(matchHistNo, request.getTeamBNo(), request.getScoreB());
            // 2. 경기 사진 등록
            compService.uploadHistoryImage(matchHistNo, multipartFile);
            // 3. 경기 일자 등록
            log.info("matchDate from Request => " + request.getMatchDate());
            log.info("matchHistoryNo from Request => " + request.getMatchHistNo());
            compService.updateMatchDate(request.getMatchDate(), matchHistNo);
        } catch (IOException e) {
            log.error("사진 업로드 실패 : " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return ApiResponse.success(UPDATE_HISTORY_RECORD_SUCCESS);
    }


    /**
    * 경기 기록 조회 API
    * @request : 경기 번호
    * @ response : MatchDetailResponse
     */
    @GetMapping(value = "/history/{match_hist_no}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<HistoryDetailResponse>> getMatchHistoryDetail(@PathVariable("match_hist_no") Long matchHistNo) {

        log.info("getMatchHistoryDetail ====> ");
        HistoryDetailResponse response = compService.getHistoryDetail(matchHistNo);
        return ApiResponse.success(GET_HISTORY_DETAIL_SUCCESS, response);
    }

    /**
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

    /**
     * 레이팅 반영 API (경기 종료후 결과 입력 -> Elo 알고리즘에 따라 개인 레이팅 변경)
     * @request : AfterMatchRatingRequest
     * - Long matchHistNo : 경기 번호
     * - winTeamNo / winTeamScore : 이긴 팀 번호 및 점수
     * - loseTeamNo / loseTeamScore : 진 팀 번호 및 점수
     * */
    @PostMapping(value = "/rating",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<Long>>> updateRating(@RequestBody AfterMatchRatingRequest request) {
        // 레이팅 변경하고 변동값을 리턴
        List<Long> ratingChange = compService.updateRating(request);
        log.info("레이팅 변동 : " + ratingChange);
        return ApiResponse.success(UPDATE_RATING_SUCCESS, ratingChange);
    }
    /**
     @author: 김동욱
     @description: 경쟁 - 내기 상품 불러오기
     @request : N/A
     @response : ProductList
     */
    @GetMapping("/products")
    public ResponseEntity<ApiResponse<List<GetProductResponse>>> getProducts() {


        return ApiResponse.success(GET_PRODUCT_DETAIL_SUCCESS, compService.getProducts());
    }

    // 상위 num 명의 랭크 (레이팅 순)
    @GetMapping(value = "/rank", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<RankResponse>>> getRankingList(@RequestParam("num") int num) {

        List<RankResponse> list = compService.getRankList(num);
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
     * @reqyest 두 팀의 번호(team_no)
     *
     */
    @PostMapping(value = "/match", consumes =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Long>> createMatch(@RequestBody CreateMatchRequest request) {

        log.info("[POST] /comp/match : 경기 생성 ===> ");
        log.info("TEAM 1 NO : " + request.getTeam1No() + " , TEAM 2 NO : " + request.getTeam2No());
        Long matchHistoryNo = 0L;
        try {
            List<Long> memberList1 = compService.getTeamMemberList(request.getTeam1No()); // 1팀 명단
            List<Long> memberList2 = compService.getTeamMemberList(request.getTeam2No()); // 2팀 명단
            String loc1 = compService.getTeamInfo(request.getTeam1No()).getTeamLoc();
            String loc2 = compService.getTeamInfo(request.getTeam2No()).getTeamLoc();
            String matchLoc = (Math.random() > 0.5) ? loc1 : loc2;
            request.setMatchLoc(matchLoc);
            log.info("TEAM 1 MEMBERS : " +  memberList1);
            log.info("TEAM 2 MEMBERS : " +  memberList2);
            log.info("matchLoc : " + matchLoc);
            matchHistoryNo = compService.generateMatch(request);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        log.info("return matchHistoryNo : " + matchHistoryNo);
        return ApiResponse.success(MATCH_CREATED, matchHistoryNo);
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

    @RequestMapping("/kakaopay")
    public ResponseEntity<ApiResponse<String>> kakaopay(HttpSession session, @RequestBody SettleDTO sdto) {

    	 return ApiResponse.success(GET_KAKAOPAY_CALL, compService.kakaopay(session, sdto));

    }

    // 패배팀 결제에 필요한 정보
    @GetMapping(value = "settle/{matchHistNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SettleResponse> getSettleInfo(@PathVariable("matchHistNo")Long matchHistNo) {
        log.info("loseTeamSettleInfo => matchHistNo : " + matchHistNo);
        SettleResponse response = new SettleResponse();
        try {
            response = compService.getSettleInfo(matchHistNo);
        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
        // 아래 successType 변경 필요
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/memberInfo")
    public ResponseEntity<Long> getMemberInfo(HttpServletRequest request) {
        String memberId = userService.getUserDetails(request);
        log.info(memberId);

        return ResponseEntity.ok(compService.findMemberNo(memberId));
    }


    /* 두 팀중 로그인한 사용자가 속한 팀의 번호 리턴해주는 API */
    @PostMapping(value = "/team/member", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> getMyTeamNo(@RequestBody ConfigureTeamRequest request) {
        String memberId = request.getMemberId();
        log.info("memberId => " + memberId);

        Long myTeamNo = 0L;
        try{
            myTeamNo = compService.getMyTeamNo(request);
            log.info("myTeamNo : " + myTeamNo);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return new ResponseEntity<>(myTeamNo, HttpStatus.OK);
    }
}