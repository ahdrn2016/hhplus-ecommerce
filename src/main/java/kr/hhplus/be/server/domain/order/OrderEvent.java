package kr.hhplus.be.server.domain.order;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderEvent {

    public static Completed of(Order order) {
        return Completed.builder()
                .orderId(order.getId())
                .totalAmount(order.getTotalAmount())
                .messageId(UUID.randomUUID().toString())
                .build();
    }

    public record Completed(
            Long orderId,
            BigDecimal totalAmount,
            String messageId
    ) {
        @Builder
        public Completed {}
    }

}
