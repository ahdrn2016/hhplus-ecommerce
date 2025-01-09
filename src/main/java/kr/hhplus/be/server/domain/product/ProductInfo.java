package kr.hhplus.be.server.domain.product;

import kr.hhplus.be.server.interfaces.api.product.ProductResponse;
import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

public class ProductInfo {

    public record PopularProduct(Long id, String name, int price) {
        @Builder
        public PopularProduct {}
    }

    public static Page<ProductResponse.Product> toResponse(Page<Product> products) {
        List<ProductResponse.Product> productResponse = products.stream()
                .map(ProductResponse.Product::of)
                .collect(Collectors.toList());

        Pageable pageable = products.getPageable();
        long totalElements = products.getTotalElements();

        return new PageImpl<>(productResponse, pageable, totalElements);
    }


    public static List<ProductResponse.Product> toResponse(List<PopularProduct> products) {
        return products.stream()
                .map(ProductResponse.Product::of)
                .collect(Collectors.toList());
    }
}