package kr.hhplus.be.server.domain.order;

import lombok.Builder;

import java.math.BigDecimal;
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
            BigDecimal price,
            int quantity
    ) {
    }
}
