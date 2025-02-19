package kr.hhplus.be.server.domain.order;

import lombok.Builder;

import java.math.BigDecimal;

public class OrderEvent {

    public static Completed of(Order order) {
        return Completed.builder()
                .orderId(order.getId())
                .totalAmount(order.getTotalAmount())
                .build();
    }

    public record Completed(
            Long orderId,
            BigDecimal totalAmount
    ) {
        @Builder
        public Completed {}
    }

}
