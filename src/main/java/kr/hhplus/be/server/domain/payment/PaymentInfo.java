package kr.hhplus.be.server.domain.payment;

import lombok.Builder;

public class PaymentInfo {
    public static Payment of(kr.hhplus.be.server.domain.payment.Payment payment) {
        return Payment.builder()
                .orderId(payment.getOrderId())
                .paymentAmount(payment.getPaymentAmount())
                .build();
    }

    public record Payment(
            Long orderId,
            int paymentAmount
    ) {
        @Builder
        public Payment {}
    }
}
