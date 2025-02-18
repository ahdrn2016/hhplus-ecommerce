package kr.hhplus.be.server.interfaces.api.order;


import kr.hhplus.be.server.application.order.OrderCriteria;

import java.util.List;

public class OrderRequest {

    public record Order(
            Long userId,
            Long issuedCouponId,
            List<OrderProduct> products
    ) {
        public OrderCriteria.Order toCriteria() {
            return OrderCriteria.Order.builder()
                    .userId(userId)
                    .issuedCouponId(issuedCouponId)
                    .products(products.stream()
                            .map(OrderProduct::toCriteria)
                            .toList())
                    .build();
        }
    }

    public record OrderProduct(
            Long productId, 
            int quantity
    ) {
        public OrderCriteria.OrderProduct toCriteria() {
            return OrderCriteria.OrderProduct.builder()
                    .productId(productId)
                    .quantity(quantity)
                    .build();
        }
    }
}
