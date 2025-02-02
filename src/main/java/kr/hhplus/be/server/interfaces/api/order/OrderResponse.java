package kr.hhplus.be.server.interfaces.api.order;

import kr.hhplus.be.server.application.order.OrderResult;
import lombok.Builder;

import java.math.BigDecimal;

public class OrderResponse {

    public static Payment of(OrderResult.Payment payment) {
        return Payment.builder()
                .orderId(payment.orderId())
                .paymentAmount(payment.paymentAmount())
                .build();
    }

    public record Payment(
            Long orderId,
            BigDecimal paymentAmount
    ) {
        @Builder
        public Payment {}
    }
}
