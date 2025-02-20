package kr.hhplus.be.server.interfaces.listener;

import kr.hhplus.be.server.domain.order.OrderEvent;
import kr.hhplus.be.server.domain.order.OrderMessageProducer;
import kr.hhplus.be.server.domain.outbox.Outbox;
import kr.hhplus.be.server.domain.outbox.OutboxService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.event.TransactionPhase.*;

@Component
@RequiredArgsConstructor
public class OrderEventListener {

    private static final String ORDER_EVENT = "order";

    @Value("${commerce-api.order.topic-name}")
    private String orderTopic;

    private final OutboxService outboxService;
    private final OrderMessageProducer orderMessageProducer;

    @TransactionalEventListener(phase = BEFORE_COMMIT)
    public void createOutbox(OrderEvent.Completed order) {
        outboxService.createOutbox(Outbox.of(ORDER_EVENT, order));
    }

    @Async
    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void sendOrderData(OrderEvent.Completed order) {
        orderMessageProducer.send(orderTopic, order);
    }

}
