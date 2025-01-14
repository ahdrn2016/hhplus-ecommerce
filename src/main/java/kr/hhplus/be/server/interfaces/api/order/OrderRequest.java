package kr.hhplus.be.server.interfaces.api.order;

import kr.hhplus.be.server.application.order.OrderCriteria;

import java.util.List;
import java.util.stream.Collectors;

public class OrderRequest {

    public record CreateOrder(
            Long userId,
            Long couponId,
            List<OrderProduct> products
    ) {
        public OrderCriteria.CreateOrder toCriteria() {
            return OrderCriteria.CreateOrder.builder()
                    .userId(userId)
                    .couponId(couponId)
                    .products(
                            products.stream()
                                    .map(OrderCriteria.OrderProduct::of)
                                    .collect(Collectors.toList())
                    )
                    .build();
        }
    }

    public record OrderProduct(Long productId, int quantity) {}
}
