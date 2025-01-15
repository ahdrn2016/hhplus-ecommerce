package kr.hhplus.be.server.domain.order;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrderDetail is a Querydsl query type for OrderDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderDetail extends EntityPathBase<OrderDetail> {

    private static final long serialVersionUID = 482866818L;

    public static final QOrderDetail orderDetail = new QOrderDetail("orderDetail");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public QOrderDetail(String variable) {
        super(OrderDetail.class, forVariable(variable));
    }

    public QOrderDetail(Path<? extends OrderDetail> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrderDetail(PathMetadata metadata) {
        super(OrderDetail.class, metadata);
    }

}

