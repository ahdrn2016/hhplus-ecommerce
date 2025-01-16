package kr.hhplus.be.server.interfaces.api.order;

import kr.hhplus.be.server.application.order.OrderResult;
import lombok.Builder;

public class OrderResponse {

    public static Payment of(OrderResult.Payment payment) {
        return Payment.builder()
                .orderId(payment.orderId())
                .paymentAmount(payment.paymentAmount())
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
