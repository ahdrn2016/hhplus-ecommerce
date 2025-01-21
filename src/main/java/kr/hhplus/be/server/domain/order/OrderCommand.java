package kr.hhplus.be.server.domain.order;

import kr.hhplus.be.server.application.order.OrderCriteria;
import kr.hhplus.be.server.domain.product.ProductInfo;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

public class OrderCommand {

    public static List<OrderProduct> createOrderProducts(OrderCriteria.Order criteria, List<ProductInfo.Product> products) {
        return products.stream()
                .map(product -> {
                    Long productId = product.productId();
                    int price = product.price();
                    int quantity = criteria.products().stream()
                            .filter(p -> p.productId().equals(productId))
                            .findFirst()
                            .map(OrderCriteria.OrderProduct::quantity)
                            .orElse(0);
                    return new OrderCommand.OrderProduct(productId, price, quantity);
                })
                .collect(Collectors.toList());
    }

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
