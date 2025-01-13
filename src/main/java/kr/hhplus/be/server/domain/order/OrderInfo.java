package kr.hhplus.be.server.domain.order;


import lombok.Builder;

public class OrderInfo {

    public static OrderDto of(Order order) {
        return OrderDto.builder()
                .orderId(order.getId())
                .totalAmount(order.getTotalAmount())
                .discountAmount(order.getDiscountAmount())
                .paymentAmount(order.getPaymentAmount())
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

    public record UserCouponDto(
            Long couponId,
            int amount
    ) {
        @Builder
        public UserCouponDto {}
    }
}
