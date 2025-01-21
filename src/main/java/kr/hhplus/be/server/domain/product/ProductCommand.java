package kr.hhplus.be.server.domain.product;

import lombok.Builder;

public class ProductCommand {

    public record Product(
            Long productId,
            int quantity
    ) {
        @Builder
        public Product {}
    }
}
