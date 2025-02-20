package kr.hhplus.be.server.domain.outbox;

import kr.hhplus.be.server.domain.order.OrderEvent;
import kr.hhplus.be.server.interfaces.scheduler.OutboxScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OutboxIntegrationTest {

    @Autowired
    private OutboxRepository outboxRepository;

    @Autowired
    private OutboxScheduler outboxScheduler;

    @Test
    void 미발행된_메시지를_kafka에_재발행한다() {
        // given
        OrderEvent.Completed order = OrderEvent.Completed.builder()
                .orderId(1L)
                .totalAmount(BigDecimal.valueOf(10000))
                .messageId(UUID.randomUUID().toString())
                .build();

        outboxRepository.save(Outbox.of("order", order));

        // when
        outboxScheduler.republish();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // then
        assertThat(outboxRepository.findById(order.messageId()))
                .extracting("eventType", "status")
                .contains("order", OutboxStatus.SUCCESS);
    }

}