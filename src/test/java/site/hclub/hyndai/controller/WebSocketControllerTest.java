//package site.hclub.hyndai.controller;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.test.context.junit4.SpringRunner;
//import site.hclub.hyndai.dto.request.MatchingRequest;
//import site.hclub.hyndai.service.MatchingService;
//
//import java.util.Arrays;
//
//import static org.mockito.Mockito.*;
//
//@RunWith(SpringRunner.class)
//public class WebSocketControllerTest {
//
//    @InjectMocks
//    private WebSocketController webSocketController;
//
//    @Mock
//    private MatchingService matchingService;
//
//    @Mock
//    private SimpMessagingTemplate messagingTemplate;
//
//    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void testAddTeamToQueue() {
//        MatchingRequest matchingRequest = MatchingRequest.builder()
//                .teamNo(1L)
//                .teamMemberNo(Arrays.asList(101L, 102L))
//                .matchType("Football")
//                .matchCapacity(11)
//                .teamRating(1500L)
//                .build();
//
//        webSocketController.addTeamToQueue(matchingRequest);
//        verify(matchingService, times(1)).addToQueue(matchingRequest);
//    }
//
//    @Test
//    public void testSendMatchResult() {
//        String matchResult = "Match Result";
//        webSocketController.sendMatchResult(matchResult);
//        verify(messagingTemplate, times(1)).convertAndSend("/topic/matches", matchResult);
//    }
//}
