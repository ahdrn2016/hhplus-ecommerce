package kr.hhplus.be.server.domain.product;

import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

public class ProductInfo {

    public static Page<Product> of(Page<kr.hhplus.be.server.domain.product.Product> products) {
        List<Product> productInfo = products.stream()
                .map(Product::of)
                .collect(Collectors.toList());

        Pageable pageable = products.getPageable();
        long totalElements = products.getTotalElements();

        return new PageImpl<>(productInfo, pageable, totalElements);
    }

    public record Product(
            Long productId,
            String name,
            int price,
            ProductStatus status
    ) {
        @Builder
        public Product {}

        public static Product of(kr.hhplus.be.server.domain.product.Product product) {
            return Product.builder()
                    .productId(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .status(product.getStatus())
                    .build();
        }
    }

    public record PopularProduct(
            Long productId,
            String name,
            int price
    ) {
        public Product of() {
            return Product.builder()
                    .productId(productId)
                    .name(name)
                    .price(price)
                    .build();
        }
    }
}