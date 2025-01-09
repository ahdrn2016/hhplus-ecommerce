package kr.hhplus.be.server.domain.payment;

import kr.hhplus.be.server.application.payment.PaymentResult;
import kr.hhplus.be.server.domain.order.Order;
import lombok.Builder;

public class PaymentInfo {
    public static PaymentResult.Order toResult(Order order) {
        return PaymentResult.Order.builder()
                .orderId(order.getId())
                .userId(order.getUserId())
                .totalAmount(order.getTotalAmount())
                .discountAmount(order.getDiscountAmount())
                .paymentAmount(order.getPaymentAmount())
                .build();
    }

    public record Payment(Long orderId, int totalAmount, int discountAmount, int paymentAmount) {
        @Builder
        public Payment {}
    }
}
