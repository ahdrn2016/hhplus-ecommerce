package kr.hhplus.be.server.interfaces.api.product;

import kr.hhplus.be.server.domain.product.Product;
import lombok.Builder;

public class ProductResponse {

    public record Products(Long productId, String name, int price) {

        @Builder
        public Products {}

        public static Products of(Product product) {
            return Products.builder()
                    .productId(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .build();
        }
    }

}
