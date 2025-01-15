package kr.hhplus.be.server.domain.order;

import lombok.Builder;

import java.util.List;

public class OrderCommand {

    public record Order(
            Long userId,
            List<OrderProduct> products
    ) {
        @Builder
        public Order {}
    }

    public record OrderProduct(
            Long productId,
            int price,
            int quantity
    ) {
    }
}
