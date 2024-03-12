package site.hclub.hyndai.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import site.hclub.hyndai.dto.request.MatchingRequest;
import site.hclub.hyndai.dto.response.MatchingResponse;
import site.hclub.hyndai.mapper.CompMapper;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

/**
 * @author 이혜연
 * @description: WebSocket, Redis Queue, Scheduler를 활용한 매치
 * ===========================
      AUTHOR      NOTE
 * ---------------------------
 *    이혜연       최초 생성
 * ===========================
 */
@Slf4j
@Service
public class MatchingService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private CompMapper compMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private ScheduledFuture<?> scheduledFuture;


    private volatile boolean matchingSuccess = false;
    private static MatchingRequest myRealTeam = null;
    private int failureAttempts = 0;
    private static final int MAX_FAILURE_ATTEMPTS = 3;

    /**
     * 작성자: 이혜연
     * 처리 내용:  Redis Queue를 통해 큐에 없는 팀을 추가
     */
    public void addToQueue(MatchingRequest team) {
        List<MatchingResponse> teams = compMapper.getTeams();

        teams.stream()
                .collect(Collectors.groupingBy(MatchingResponse::getTeamNo))
                .forEach((teamNo, teamList) -> {
                    if(!redisTemplate.opsForList().range("teamQueue", 0, -1)
                            .stream()
                            .map(this::convertJsonToMatchingRequest)
                            .anyMatch(existingTeam ->
                                    existingTeam != null && existingTeam.getTeamNo().equals(teamNo))){
                    List<Long> teamMemberNoList = teamList.stream()
                            .map(MatchingResponse::getTeamMemberNo)
                            .collect(Collectors.toList());

                    MatchingRequest matchingRequest = MatchingRequest.builder()
                            .teamNo(teamNo)
                            .teamMemberNo(teamMemberNoList)
                            .matchType(teamList.get(0).getMatchType())
                            .matchCapacity(teamList.get(0).getMatchCapacity())
                            .teamRating(teamList.get(0).getTeamRating())
                            .build();


                    String teamJson = convertMatchingRequestToJson(matchingRequest);
                    redisTemplate.opsForList().leftPush("teamQueue", teamJson);
                }});

        myRealTeam = team;
    }

    /**
     * 작성자: 이혜연
     * 처리 내용:  3초에 한 번씩 스케줄링을 돌며 WebSocket을 통해 매치 결과를 프론트에 알림
     */
    @Scheduled(fixedRate = 3000)
    public void matchTeams() {
        MatchingRequest myTeam = myRealTeam;
        MatchingRequest matchingTeam = null;

        if(myTeam != null) {
            while (redisTemplate.opsForList().size("teamQueue") > 0) {
                String potentialMatchJson = redisTemplate.opsForList().rightPop("teamQueue");
                MatchingRequest potentialMatchFromRedis = convertJsonToMatchingRequest(potentialMatchJson);
                if (myTeam.getTeamNo() == potentialMatchFromRedis.getTeamNo() ||
                        !myTeam.getMatchType().equals(potentialMatchFromRedis.getMatchType()) ||
                        myTeam.getMatchCapacity() != potentialMatchFromRedis.getMatchCapacity() ||
                        Math.abs(myTeam.getTeamRating() - potentialMatchFromRedis.getTeamRating()) > 100) {
                    continue;
                }

                if (myTeam.getTeamMemberNo().stream().anyMatch(potentialMatchFromRedis.getTeamMemberNo()::contains)) {
                    continue;
                }

                matchingTeam = potentialMatchFromRedis;
                break;
            }

            if (matchingTeam != null) {
                notifyMatch(myTeam, matchingTeam);
                matchingSuccess = true;
                myRealTeam = null;
            } else {
                failureAttempts++;

                if (failureAttempts >= MAX_FAILURE_ATTEMPTS) {
                    notifyFailure();
                } else {
                    String potentialMatchJson = redisTemplate.opsForList().rightPop("teamQueue");
                    if (potentialMatchJson == null) {
                        notifyFailure();
                    }
                }
            }
        }
    }


    private void notifyMatch(MatchingRequest myTeam, MatchingRequest matchingTeam) {
        messagingTemplate.convertAndSend("/topic/matches", myTeam.getTeamNo() + "," + matchingTeam.getTeamNo());
        messagingTemplate.convertAndSendToUser(myTeam.getTeamNo().toString(), "/topic/matches", myTeam.getTeamNo() + "," + matchingTeam.getTeamNo());
        messagingTemplate.convertAndSendToUser(matchingTeam.getTeamNo().toString(), "/topic/matches", myTeam.getTeamNo() + "," + matchingTeam.getTeamNo());
    }

    private void notifyFailure() {
        messagingTemplate.convertAndSend("/topic/failure", "Matching failed after several attempts");
    }

    private String convertMatchingRequestToJson(MatchingRequest team) {
        try {
            return new ObjectMapper().writeValueAsString(team);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    private MatchingRequest convertJsonToMatchingRequest(String teamJson) {
        try {
            return new ObjectMapper().readValue(teamJson, MatchingRequest.class);
        } catch (JsonProcessingException e) {
            log.error("Error converting JSON to MatchingRequest", e);
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
