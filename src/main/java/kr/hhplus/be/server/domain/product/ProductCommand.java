package kr.hhplus.be.server.domain.product;

import lombok.Builder;

public class ProductCommand {

    public record OrderProduct(Long productId, int quantity) {
        @Builder
        public OrderProduct {}
    }
}
