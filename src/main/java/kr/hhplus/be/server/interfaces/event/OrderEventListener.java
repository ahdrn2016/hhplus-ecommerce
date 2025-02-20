package kr.hhplus.be.server.interfaces.event;

import kr.hhplus.be.server.domain.order.OrderEvent;
import kr.hhplus.be.server.domain.outbox.Outbox;
import kr.hhplus.be.server.domain.outbox.OutboxService;
import kr.hhplus.be.server.infrastructure.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.event.TransactionPhase.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventListener {

    private final OutboxService outboxService;
    private final KafkaProducer kafkaProducer;

    @TransactionalEventListener(phase = BEFORE_COMMIT)
    public void create(OrderEvent.Completed event) {
        outboxService.create(Outbox.of("order", event));
    }

    @Async
    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void handle(OrderEvent.Completed event) {
        kafkaProducer.send("order-completed.v1", event);
    }

}
