package kr.hhplus.be.server.interfaces.api.product;

import kr.hhplus.be.server.domain.product.ProductInfo;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public class ProductResponse {

    public static List<PopularProduct> of(List<ProductInfo.PopularProduct> products) {
        return products.stream()
                .map(product -> PopularProduct.builder()
                        .productId(product.productId())
                        .name(product.name())
                        .price(product.price())
                        .totalQuantity(product.totalQuantity())
                        .build())
                .toList();
    }

    public static Page<Product> of(Page<ProductInfo.Product> products) {
        return products.map(product -> Product.builder()
                        .productId(product.productId())
                        .name(product.name())
                        .price(product.price())
                        .build());
    }

    public record Product(
            Long productId,
            String name,
            BigDecimal price
    ) {
        @Builder
        public Product {}
    }

    public record PopularProduct(
            Long productId,
            String name,
            BigDecimal price,
            int totalQuantity
    ) {
        @Builder
        public PopularProduct {}
    }
}
