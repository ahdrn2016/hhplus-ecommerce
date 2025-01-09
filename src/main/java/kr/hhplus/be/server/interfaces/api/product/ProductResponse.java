package kr.hhplus.be.server.interfaces.api.product;

import kr.hhplus.be.server.domain.product.ProductInfo;
import lombok.Builder;

public class ProductResponse {

    public record Product(Long productId, String name, int price) {

        @Builder
        public Product {}

        public static Product of(kr.hhplus.be.server.domain.product.Product product) {
            return Product.builder()
                    .productId(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .build();
        }

        public static Product of(ProductInfo.PopularProduct product) {
            return Product.builder()
                    .productId(product.id())
                    .name(product.name())
                    .price(product.price())
                    .build();
        }
    }

}
