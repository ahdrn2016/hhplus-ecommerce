package kr.hhplus.be.server.application.order;

import kr.hhplus.be.server.domain.order.OrderInfo;
import lombok.Builder;

public class OrderResult {

    public static OrderDto of(OrderInfo.OrderDto order) {
        return OrderDto.builder()
                .orderId(order.orderId())
                .totalAmount(order.totalAmount())
                .discountAmount(order.discountAmount())
                .paymentAmount(order.paymentAmount())
                .build();
    }

    public record OrderDto(
            Long orderId,
            int totalAmount,
            int discountAmount,
            int paymentAmount
    ) {
        @Builder
        public OrderDto {}
    }

}
