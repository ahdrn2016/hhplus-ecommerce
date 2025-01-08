package kr.hhplus.be.server.domain.product;

import kr.hhplus.be.server.interfaces.api.product.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

public class ProductInfo {

    public static Page<ProductResponse.Products> toResponse(Page<Product> products) {
        List<ProductResponse.Products> productResponse = products.stream()
                .map(ProductResponse.Products::of)
                .collect(Collectors.toList());

        Pageable pageable = products.getPageable();
        long totalElements = products.getTotalElements();

        return new PageImpl<>(productResponse, pageable, totalElements);
    }

}