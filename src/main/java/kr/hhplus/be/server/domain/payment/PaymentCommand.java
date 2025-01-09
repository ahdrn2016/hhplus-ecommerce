package kr.hhplus.be.server.domain.payment;

import lombok.Builder;

public class PaymentCommand {

    public record Payment(Long userId, Long orderId, int totalAmount, int discountAmount, int paymentAmount) {
        @Builder
        public Payment {}
    }

}
