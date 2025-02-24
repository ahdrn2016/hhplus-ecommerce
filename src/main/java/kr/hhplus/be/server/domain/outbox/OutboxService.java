package kr.hhplus.be.server.domain.outbox;

import kr.hhplus.be.server.domain.order.OrderEvent;
import kr.hhplus.be.server.domain.order.OrderMessageProducer;
import kr.hhplus.be.server.support.util.JsonConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OutboxService {

    @Value("${commerce-api.order.topic-name}")
    private String orderTopic;

    private final OutboxRepository outboxRepository;
    private final OrderMessageProducer orderMessageProducer;

    @Transactional
    public Outbox createOutbox(Outbox outbox) {
        return outboxRepository.save(outbox);
    }

    @Transactional
    public void updateOutbox(String messageId) {
        Outbox outbox = outboxRepository.findById(messageId);
        outbox.success();
    }

    public void republish() {
        LocalDateTime schedulerTime = LocalDateTime.now().minusMinutes(5);
        List<Outbox> outboxes = outboxRepository.findAllByStatus(OutboxStatus.INIT);

        for (Outbox outbox : outboxes) {
            if (outbox.getCreatedAt().isAfter(schedulerTime)) {
                orderMessageProducer.send(orderTopic, JsonConverter.fromJson(outbox.getPayload(), OrderEvent.Completed.class));
            }
        }
    }
}
