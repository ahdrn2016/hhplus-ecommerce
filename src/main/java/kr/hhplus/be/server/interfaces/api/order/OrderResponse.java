package kr.hhplus.be.server.interfaces.api.order;

import kr.hhplus.be.server.application.order.OrderResult;
import lombok.Builder;

public class OrderResponse {

    public static Order of(OrderResult.Order order) {
        return Order.builder()
                .orderId(order.orderId())
                .totalAmount(order.totalAmount())
                .discountAmount(order.discountAmount())
                .paymentAmount(order.paymentAmount())
                .build();
    }

    public record Order(
            Long orderId,
            int totalAmount,
            int discountAmount,
            int paymentAmount
    ) {
        @Builder
        public Order {}
    }
}
