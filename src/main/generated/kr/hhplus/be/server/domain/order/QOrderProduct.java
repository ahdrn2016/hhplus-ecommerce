package kr.hhplus.be.server.domain.order;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrderProduct is a Querydsl query type for OrderProduct
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderProduct extends EntityPathBase<OrderProduct> {

    private static final long serialVersionUID = 216773854L;

    public static final QOrderProduct orderProduct = new QOrderProduct("orderProduct");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public QOrderProduct(String variable) {
        super(OrderProduct.class, forVariable(variable));
    }

    public QOrderProduct(Path<? extends OrderProduct> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrderProduct(PathMetadata metadata) {
        super(OrderProduct.class, metadata);
    }

}

