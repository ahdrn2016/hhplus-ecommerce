package kr.hhplus.be.server.domain.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ProductRepository {

    Page<Product> findAllByProductStatus(ProductStatus productStatus, PageRequest pageable);

    Product findPopularProducts();
}
