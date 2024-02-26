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
import java.util.stream.Collectors;

@Slf4j
@Service
public class MatchingService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private CompMapper compMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private volatile boolean matchingSuccess = false;
    private static MatchingRequest myRealTeam = null;
    private int failureAttempts = 0;
    private static final int MAX_FAILURE_ATTEMPTS = 3;

    public void addToQueue(MatchingRequest team) {
        System.out.println(team.getTeamNo());
        myRealTeam = team;

        List<MatchingResponse> teams = compMapper.getTeams();

        teams.stream()
                .collect(Collectors.groupingBy(MatchingResponse::getTeamNo))
                .forEach((teamNo, teamList) -> {
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

                    System.out.println("종목=" + matchingRequest.getMatchType());
                    System.out.println("경기인원수=" + teamMemberNoList.size());

                    // Redis의 List에 팀 정보 저장
                    String teamJson = convertMatchingRequestToJson(matchingRequest);
                    redisTemplate.opsForList().leftPush("teamQueue", teamJson);
                });
    }

    @Scheduled(fixedRate = 1000)
    public void matchTeams() {
        if (!matchingSuccess && redisTemplate.opsForList().size("teamQueue") > 1) {

            MatchingRequest myTeam = myRealTeam;
            String myTeamJson = convertMatchingRequestToJson(myTeam);

            MatchingRequest matchingTeam = findMatchingTeam(myTeam);
            System.out.println("성공="+matchingTeam);

            if (matchingTeam != null) {
                notifyMatch(myTeam, matchingTeam);
                matchingSuccess = true;
            } else {
                failureAttempts++;

                if(failureAttempts >= MAX_FAILURE_ATTEMPTS){
                    notifyFailure();
                } else{
                    redisTemplate.opsForList().leftPush("teamQueue", myTeamJson);
                }
            }
        }
    }

    private MatchingRequest findMatchingTeam(MatchingRequest myTeam) {
        while (redisTemplate.opsForList().size("teamQueue") > 0) {
            // Redis의 List에서 팀 정보 가져오기
            String potentialMatchJson = redisTemplate.opsForList().rightPop("teamQueue");
            MatchingRequest potentialMatchFromRedis = convertJsonToMatchingRequest(potentialMatchJson);
            System.out.println("myTeam="+myTeam.getTeamNo()+" potential="+potentialMatchFromRedis.getTeamNo());
            if (myTeam.getTeamNo() == potentialMatchFromRedis.getTeamNo() ||
                    !myTeam.getMatchType().equals(potentialMatchFromRedis.getMatchType()) ||
                    myTeam.getMatchCapacity() != potentialMatchFromRedis.getMatchCapacity() ||
                    Math.abs(myTeam.getTeamRating() - potentialMatchFromRedis.getTeamRating()) > 100) {
                continue;
            }

            if (myTeam.getTeamMemberNo().stream().anyMatch(potentialMatchFromRedis.getTeamMemberNo()::contains)) {
                continue;
            }

            return potentialMatchFromRedis;
        }

        return null;
    }

    private void notifyMatch(MatchingRequest myTeam, MatchingRequest matchingTeam) {
        messagingTemplate.convertAndSend("/topic/matches", myTeam + " vs " + matchingTeam);
        messagingTemplate.convertAndSendToUser(myTeam.getTeamNo().toString(), "/topic/matches", myTeam + " vs " + matchingTeam);
        messagingTemplate.convertAndSendToUser(matchingTeam.getTeamNo().toString(), "/topic/matches", myTeam + " vs " + matchingTeam);
    }

    private void notifyFailure() {
        messagingTemplate.convertAndSend("/topic/failure", "Matching failed after several attempts");
    }

    private String convertMatchingRequestToJson(MatchingRequest team) {
        try {
            return new ObjectMapper().writeValueAsString(team);
        } catch (JsonProcessingException e) {
            log.error("Error converting MatchingRequest to JSON", e);
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