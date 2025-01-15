package kr.hhplus.be.server.interfaces.api.order;


import kr.hhplus.be.server.application.order.OrderCriteria;

import java.util.List;

public class OrderRequest {

    public record Order(
            Long userId,
            Long couponId,
            List<OrderDetail> products
    ) {
        public OrderCriteria.Order toCriteria() {
            return OrderCriteria.Order.builder()
                    .userId(userId)
                    .couponId(couponId)
                    .products(products.stream()
                            .map(OrderDetail::toCriteria)
                            .toList())
                    .build();
        }
    }

    public record OrderDetail(
            Long productId, 
            int quantity
    ) {
        public OrderCriteria.OrderDetail toCriteria() {
            return OrderCriteria.OrderDetail.builder()
                    .productId(productId)
                    .quantity(quantity)
                    .build();
        }
    }
}
