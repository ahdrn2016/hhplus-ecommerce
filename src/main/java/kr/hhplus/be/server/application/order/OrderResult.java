package kr.hhplus.be.server.application.order;

import kr.hhplus.be.server.domain.order.OrderInfo;
import lombok.Builder;

public class OrderResult {

    public static Order of(OrderInfo.Order order) {
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
