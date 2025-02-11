package kr.hhplus.be.server.infrastructure.product;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.hhplus.be.server.domain.order.OrderStatus;
import kr.hhplus.be.server.domain.order.QOrder;
import kr.hhplus.be.server.domain.order.QOrderProduct;
import kr.hhplus.be.server.domain.product.ProductInfo;
import kr.hhplus.be.server.domain.product.QProduct;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ProductInfo.PopularProduct> getPopularProducts() {
        QOrderProduct orderProduct = QOrderProduct.orderProduct;
        QOrder order = QOrder.order;
        QProduct product = QProduct.product;

        return queryFactory
                .select(Projections.constructor(
                        ProductInfo.PopularProduct.class,
                        product.id,
                        product.name,
                        product.price,
                        orderProduct.quantity.sum().as("totalQuantity")
                ))
                .from(orderProduct)
                .join(order).on(orderProduct.order.id.eq(order.id))
                .join(product).on(orderProduct.productId.eq(product.id))
                .where(
                        order.status.eq(OrderStatus.COMPLETE),
                        order.createdAt.goe(LocalDateTime.now().minusDays(3))
                )
                .groupBy(product.id)
                .orderBy(orderProduct.quantity.sum().desc())
                .limit(5)
                .fetch();
    }

}
