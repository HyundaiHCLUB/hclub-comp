package site.hclub.hyndai.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import site.hclub.hyndai.service.CompService;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class ScheduledTasks {

    private final CompService compService;

    // 1분 단위로 시간이 지난 경기를 제거합니다.
    @Scheduled(cron = "0 0/1 * * * ?")
    public void updateTeamMatchStatus() {
        compService.updateMatchStatus();
    }
}
