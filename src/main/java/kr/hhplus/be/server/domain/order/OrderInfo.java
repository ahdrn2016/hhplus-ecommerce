package kr.hhplus.be.server.domain.order;


import lombok.Builder;

public class OrderInfo {

    public static Order of(kr.hhplus.be.server.domain.order.Order order) {
        return Order.builder()
                .orderId(order.getId())
                .totalAmount(order.getTotalAmount())
                .discountAmount(order.getDiscountAmount())
                .paymentAmount(order.getPaymentAmount())
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
