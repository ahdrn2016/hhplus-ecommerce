package kr.hhplus.be.server.infrastructure.product;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kr.hhplus.be.server.domain.order.QOrder;
import kr.hhplus.be.server.domain.order.QOrderProduct;
import kr.hhplus.be.server.domain.product.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    @PersistenceContext
    private final EntityManager em;

    private final ProductJpaRepository productJpaRepository;

    @Override
    public Page<Product> findAllByStatus(ProductStatus status, PageRequest pageable) {
        return productJpaRepository.findAllByStatus(status, pageable);
    }

    @Override
    public List<ProductInfo.PopularProduct> findPopularProducts() {
        QOrderProduct qOrderProduct = QOrderProduct.orderProduct;
        QOrder qOrder = QOrder.order;
        QProduct qProduct = QProduct.product;

        LocalDateTime threeDaysAgo = LocalDateTime.now().minusDays(3);

        JPAQuery<ProductInfo> query = new JPAQuery<>(em);

        List<ProductInfo.PopularProduct> result = query.select(Projections.constructor(
                        ProductInfo.PopularProduct.class,
                        qProduct.id,
                        qProduct.name,
                        qProduct.price,
                        qOrderProduct.quantity.sum().as("totalQuantity")
                ))
                .from(qOrderProduct)
                .join(qOrder).on(qOrderProduct.orderId.eq(qOrder.id))
                .join(qProduct).on(qOrderProduct.productId.eq(qProduct.id))
                .where(qOrder.createdAt.goe(threeDaysAgo))
                .groupBy(qProduct.id, qProduct.name, qProduct.price)
                .orderBy(qOrderProduct.quantity.sum().desc())
                .limit(5)
                .fetch();

        return result;
    }

    @Override
    public boolean existsByIdInAndStatus(List<Long> productIds, ProductStatus status) {
        return productJpaRepository.existsByIdInAndStatus(productIds, status);
    }

    @Override
    public List<Product> findAllByIdIn(List<Long> productIds) {
        return productJpaRepository.findAllByIdIn(productIds);
    }

}
