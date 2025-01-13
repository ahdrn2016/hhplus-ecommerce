package kr.hhplus.be.server.domain.product;

import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

public class ProductInfo {

    public static Page<ProductDto> of(Page<Product> products) {
        List<ProductDto> productInfo = products.stream()
                .map(ProductDto::of)
                .collect(Collectors.toList());

        Pageable pageable = products.getPageable();
        long totalElements = products.getTotalElements();

        return new PageImpl<>(productInfo, pageable, totalElements);
    }

    public static List<ProductDto> of(List<PopularProductDto> products) {
        return products.stream()
                .map(PopularProductDto::of)
                .collect(Collectors.toList());
    }

    public record ProductDto(Long productId, String name, int price) {
        @Builder
        public ProductDto {}

        public static ProductDto of(Product product) {
            return ProductDto.builder()
                    .productId(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .build();
        }
    }

    public record PopularProductDto(Long productId, String name, int price) {

        // TODO
        public ProductDto of() {
            return ProductDto.builder()
                    .productId(productId)
                    .build();
        }
    }
}