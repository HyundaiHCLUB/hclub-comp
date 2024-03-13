package site.hclub.hyndai.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import site.hclub.hyndai.dto.request.MatchingRequest;
import site.hclub.hyndai.service.MatchingService;

/**
 * @author 이혜연
 * @description: 메시지 매핑 메서드 정의
 * ===========================
      AUTHOR      NOTE
 * ---------------------------
 *    이혜연       최초 생성
 * ===========================
 */
@Controller
public class WebSocketController {

    @Autowired
    private MatchingService matchingService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @MessageMapping("/addTeamToQueue")
    public void addTeamToQueue(MatchingRequest matchingRequest) {
        matchingService.addToQueue(matchingRequest);
    }


    @MessageMapping("/topic/matches")
    public void sendMatchResult(String matchResult) {
        messagingTemplate.convertAndSend("/topic/matches", matchResult);
    }
}


