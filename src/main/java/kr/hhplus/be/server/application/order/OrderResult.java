package kr.hhplus.be.server.application.order;

import kr.hhplus.be.server.domain.payment.PaymentInfo;
import lombok.Builder;

public class OrderResult {

    public static Payment of(PaymentInfo.Payment payment) {
        return Payment.builder()
                .orderId(payment.orderId())
                .paymentAmount(payment.paymentAmount())
                .build();
    }

    public record Payment(
            Long orderId,
            Long userId,
            int paymentAmount
    ) {
        @Builder
        public Payment {}
    }

}
