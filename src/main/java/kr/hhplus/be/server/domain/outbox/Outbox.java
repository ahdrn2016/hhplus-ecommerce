package kr.hhplus.be.server.domain.outbox;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.BaseEntity;
import kr.hhplus.be.server.domain.order.OrderEvent;
import kr.hhplus.be.server.support.util.JsonConverter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Outbox extends BaseEntity {

    @Id
    private String messageId;

    private String eventType;

    @Enumerated(EnumType.STRING)
    private OutboxStatus status;

    private String payload;

    @Builder
    public Outbox(String messageId, String eventType, OutboxStatus status, String payload) {
        this.messageId = messageId;
        this.eventType = eventType;
        this.status = status;
        this.payload = payload;
    }

    public static Outbox of(String eventType, OrderEvent.Completed order) {
        return Outbox.builder()
                .messageId(order.messageId())
                .eventType(eventType)
                .status(OutboxStatus.INIT)
                .payload(JsonConverter.toJson(order))
                .build();
    }

    public void success() {
        this.status = OutboxStatus.SUCCESS;
    }

}
