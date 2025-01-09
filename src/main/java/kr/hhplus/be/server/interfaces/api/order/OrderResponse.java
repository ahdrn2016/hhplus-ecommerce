package kr.hhplus.be.server.interfaces.api.order;

import lombok.Builder;

public class OrderResponse {

    public record Order(Long orderId, int totalAmount, int discountAmount, int paymentAmount) {
        @Builder
        public Order {}
    }
}
