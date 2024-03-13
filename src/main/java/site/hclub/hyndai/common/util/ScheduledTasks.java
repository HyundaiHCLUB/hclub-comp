package site.hclub.hyndai.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import site.hclub.hyndai.service.CompService;
/**
 * @author 김동욱
 * @description: 만료된 팀 관리 스케줄러
 * ===========================
AUTHOR      NOTE
 * ---------------------------
 *    김동욱        최초생성
 * ===========================
 */
@Component
@EnableScheduling
@RequiredArgsConstructor
public class ScheduledTasks {

    private final CompService compService;

    // 1분 단위로 매칭 시간이 만료된 팀들을 제거합니다.
    @Scheduled(cron = "0 0/1 * * * ?")
    public void updateTeamMatchStatus() {
        compService.updateMatchStatus();
    }
}
