package kr.hhplus.be.server.domain.coupon;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserCoupon is a Querydsl query type for UserCoupon
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserCoupon extends EntityPathBase<UserCoupon> {

    private static final long serialVersionUID = 1311004348L;

    public static final QUserCoupon userCoupon = new QUserCoupon("userCoupon");

    public final NumberPath<Long> couponId = createNumber("couponId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<UserCouponStatus> status = createEnum("status", UserCouponStatus.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QUserCoupon(String variable) {
        super(UserCoupon.class, forVariable(variable));
    }

    public QUserCoupon(Path<? extends UserCoupon> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserCoupon(PathMetadata metadata) {
        super(UserCoupon.class, metadata);
    }

}

