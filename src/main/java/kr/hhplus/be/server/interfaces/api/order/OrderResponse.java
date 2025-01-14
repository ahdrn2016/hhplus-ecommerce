package kr.hhplus.be.server.interfaces.api.order;

import kr.hhplus.be.server.application.order.OrderResult;
import lombok.Builder;

public class OrderResponse {

    public static OrderDto of(OrderResult.OrderDto orderDto) {
        return OrderDto.builder()
                .orderId(orderDto.orderId())
                .totalAmount(orderDto.totalAmount())
                .discountAmount(orderDto.discountAmount())
                .paymentAmount(orderDto.paymentAmount())
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
