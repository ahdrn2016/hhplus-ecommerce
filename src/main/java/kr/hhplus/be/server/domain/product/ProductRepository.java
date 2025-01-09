package kr.hhplus.be.server.domain.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ProductRepository {

    Page<Product> findAllByStatus(ProductStatus status, PageRequest pageable);

    List<ProductInfo.PopularProduct> findPopularProducts();

    boolean existsByIdInAndStatus(List<Long> productIds, ProductStatus status);

    List<Product> findAllByIdIn(List<Long> productIds);
}
