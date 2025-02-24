package kr.hhplus.be.server.infrastructure.order;

import kr.hhplus.be.server.domain.order.OrderEvent;
import kr.hhplus.be.server.domain.order.OrderMessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderMessageProducerImpl implements OrderMessageProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void send(String topic, OrderEvent.Completed order) {
        kafkaTemplate.send(topic, order);
        log.info("send to data platform: {}", order);
    }

}
