package kr.hhplus.be.server.domain.order;

import lombok.Builder;

public class OrderCommand {

    public record OrderProduct(Long productId, int quantity) {
        @Builder
        public OrderProduct {}
    }

}
