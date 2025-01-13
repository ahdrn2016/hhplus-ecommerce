package kr.hhplus.be.server.interfaces.api.product;

import kr.hhplus.be.server.domain.product.ProductInfo;
import lombok.Builder;
import org.springframework.data.domain.Page;

public class ProductResponse {

    public static Page<ProductDto> of(Page<ProductInfo.ProductDto> productInfo) {
        return null;
    }

    public record ProductDto(Long productId, String name, int price) {

        @Builder
        public ProductDto {}

        public static ProductDto of(kr.hhplus.be.server.domain.product.Product product) {
            return ProductDto.builder()
                    .productId(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .build();
        }

        public static ProductDto of(ProductInfo.PopularProductDto product) {
            return ProductDto.builder()
                    .productId(product.id())
                    .name(product.name())
                    .price(product.price())
                    .build();
        }
    }

}
