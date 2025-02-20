package kr.hhplus.be.server.interfaces.scheduler;

import kr.hhplus.be.server.domain.outbox.OutboxService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxScheduler {

    private final OutboxService outboxService;

    @Scheduled(cron = "0 */5 * * * *")
    public void republish() {
        outboxService.republish();
    }

}
