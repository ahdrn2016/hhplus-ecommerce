package kr.hhplus.be.server.infrastructure.product;

import kr.hhplus.be.server.domain.product.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    @Override
    public Page<Product> findAllByStatus(ProductStatus status, PageRequest pageable) {
        return productJpaRepository.findAllByStatus(status, pageable);
    }

    @Override
    public List<ProductInfo.PopularProduct> findPopularProducts() {
        return productJpaRepository.findPopularProducts();
    }

    @Override
    public boolean existsByIdInAndStatus(List<Long> productIds, ProductStatus status) {
        return productJpaRepository.existsByIdInAndStatus(productIds, status);
    }

    @Override
    public List<Product> findAllByIdInWithLock(List<Long> productIds) {
        return productJpaRepository.findAllByIdInWithLock(productIds);
    }

    @Override
    public Product findById(long id) {
        return productJpaRepository.findById(id).orElse(null);
    }

}
