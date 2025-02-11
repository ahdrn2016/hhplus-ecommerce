package kr.hhplus.be.server.domain.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ProductRepository {

    Page<Product> findAllByStatus(ProductStatus status, PageRequest pageRequest);

    List<ProductInfo.PopularProduct> getPopularProducts();

    boolean existsByIdInAndStatus(List<Long> productIds, ProductStatus status);

    List<Product> findAllByIdInWithLock(List<Long> productIds);

    Product findById(long id);
}
