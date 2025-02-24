package kr.hhplus.be.server.interfaces.consumer;

import kr.hhplus.be.server.domain.order.OrderEvent;
import kr.hhplus.be.server.domain.outbox.OutboxService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMessageConsumer {

    private final OutboxService outboxService;

    @KafkaListener(topics = "${commerce-api.order.topic-name}", groupId = "order")
    public void updateOutbox(OrderEvent.Completed order) {
        outboxService.updateOutbox(order.messageId());
    }

}
