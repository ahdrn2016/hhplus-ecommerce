package kr.hhplus.be.server.application.payment;

import kr.hhplus.be.server.domain.payment.PaymentCommand;
import lombok.Builder;

public class PaymentResult {

    public record Order(Long orderId, Long userId, int totalAmount, int discountAmount, int paymentAmount) {
        @Builder
        public Order {}

        public PaymentCommand.Payment toCommand() {
            return PaymentCommand.Payment.builder()
                    .orderId(orderId)
                    .userId(userId)
                    .totalAmount(totalAmount)
                    .discountAmount(discountAmount)
                    .paymentAmount(paymentAmount)
                    .build();
        }
    }
}
