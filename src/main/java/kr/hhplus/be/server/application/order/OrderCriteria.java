package kr.hhplus.be.server.application.order;

import lombok.Builder;

import java.util.List;

public class OrderCriteria {

    public record Order(
            Long userId,
            Long couponId,
            List<OrderDetail> products
    ) {
        @Builder
        public Order {}
    }

    public record OrderDetail(
            Long productId,
            int quantity
    ) {
        @Builder
        public OrderDetail {}
    }
}
