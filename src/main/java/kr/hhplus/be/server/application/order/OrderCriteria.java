package kr.hhplus.be.server.application.order;

import kr.hhplus.be.server.domain.product.ProductCommand;
import lombok.Builder;

import java.util.List;

public class OrderCriteria {

    public record Order(
            Long userId,
            Long couponId,
            List<OrderProduct> products
    ) {
        @Builder
        public Order {}

        public List<ProductCommand.Product> toProductCommand() {
            return products.stream()
                    .map(product -> ProductCommand.Product.builder()
                            .productId(product.productId)
                            .quantity(product.quantity)
                            .build())
                    .toList();
        }
    }

    public record OrderProduct(
            Long productId,
            int quantity
    ) {
        @Builder
        public OrderProduct {}
    }
}
