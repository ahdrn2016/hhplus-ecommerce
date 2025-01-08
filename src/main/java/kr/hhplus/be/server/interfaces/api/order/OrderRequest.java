package kr.hhplus.be.server.interfaces.api.order;

import kr.hhplus.be.server.application.order.OrderParam;

import java.util.List;
import java.util.stream.Collectors;

public class OrderRequest {

    public record CreateOrder(
            Long userId,
            Long couponId,
            List<OrderProduct> products
    ) {
        public OrderParam.CreateOrder toParam() {
            return OrderParam.CreateOrder.builder()
                    .userId(userId)
                    .couponId(couponId)
                    .products(
                            products.stream()
                                    .map(OrderParam.OrderProduct::of)
                                    .collect(Collectors.toList())
                    )
                    .build();
        }
    }

    public record OrderProduct(Long productId, int quantity) {}
}
