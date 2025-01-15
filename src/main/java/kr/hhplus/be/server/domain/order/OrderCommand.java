package kr.hhplus.be.server.domain.order;

import lombok.Builder;

public class OrderCommand {

    public record Order(
            Long userId,
            int totalAmount
    ) {
    }

    public record OrderDetail(Long productId, int quantity) {
        @Builder
        public OrderDetail {}
    }

}
