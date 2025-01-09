package kr.hhplus.be.server.infrastructure.product;

import kr.hhplus.be.server.domain.product.Product;
import kr.hhplus.be.server.domain.product.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

    boolean existsByIdInAndStatus(List<Long> productIds, ProductStatus status);

    Page<Product> findAllByStatus(ProductStatus status, PageRequest pageable);

    List<Product> findAllByIdIn(List<Long> productIds);
}
