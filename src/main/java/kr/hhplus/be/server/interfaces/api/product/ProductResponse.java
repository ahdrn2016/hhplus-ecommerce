package kr.hhplus.be.server.interfaces.api.product;

import kr.hhplus.be.server.domain.product.ProductInfo;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;

public class ProductResponse {

    public static List<ProductDto> of(List<ProductInfo.ProductDto> productDtos) {
        return productDtos.stream()
                .map(productDto -> ProductDto.builder()
                        .productId(productDto.productId())
                        .name(productDto.name())
                        .price(productDto.price())
                        .build())
                .toList();
    }

    public static Page<ProductDto> of(Page<ProductInfo.ProductDto> productDtos) {
        return productDtos.map(productDto -> ProductDto.builder()
                .productId(productDto.productId())
                .name(productDto.name())
                .price(productDto.price())
                .build());
    }

    public record ProductDto(
            Long productId,
            String name,
            int price
    ) {
        @Builder
        public ProductDto {}
    }

}
