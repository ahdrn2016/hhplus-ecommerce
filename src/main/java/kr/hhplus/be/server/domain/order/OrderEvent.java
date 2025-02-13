package kr.hhplus.be.server.domain.order;

import lombok.Builder;

import java.math.BigDecimal;

public class OrderEvent {

    public static Complete of(Order order) {
        return Complete.builder()
                .orderId(order.getId())
                .totalAmount(order.getTotalAmount())
                .build();
    }

    public record Complete(
            Long orderId,
            BigDecimal totalAmount
    ) {
        @Builder
        public Complete {}
    }

}
