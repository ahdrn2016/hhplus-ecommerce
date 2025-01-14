package kr.hhplus.be.server.application.order;

import kr.hhplus.be.server.domain.product.ProductCommand;
import kr.hhplus.be.server.interfaces.api.order.OrderRequest;
import lombok.Builder;

import java.util.List;

public class OrderCriteria {

    public record CreateOrder(
            Long userId,
            Long couponId,
            List<OrderProduct> products
    ) {
        @Builder
        public CreateOrder {}
    }

    public record OrderProduct(Long productId, int quantity) {
        @Builder
        public OrderProduct {}

        public static OrderProduct of(OrderRequest.OrderProduct orderProductRequest) {
            return OrderProduct.builder()
                    .productId(orderProductRequest.productId())
                    .quantity(orderProductRequest.quantity())
                    .build();
        }

        public ProductCommand.OrderProduct toCommand() {
            return ProductCommand.OrderProduct.builder()
                    .productId(productId)
                    .quantity(quantity)
                    .build();
        }
    }


}
