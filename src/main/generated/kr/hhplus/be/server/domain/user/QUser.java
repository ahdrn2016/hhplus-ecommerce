package kr.hhplus.be.server.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1804616497L;

    public static final QUser user = new QUser("user");

    public final NumberPath<Integer> balance = createNumber("balance", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final ListPath<kr.hhplus.be.server.domain.coupon.UserCoupon, kr.hhplus.be.server.domain.coupon.QUserCoupon> userCoupons = this.<kr.hhplus.be.server.domain.coupon.UserCoupon, kr.hhplus.be.server.domain.coupon.QUserCoupon>createList("userCoupons", kr.hhplus.be.server.domain.coupon.UserCoupon.class, kr.hhplus.be.server.domain.coupon.QUserCoupon.class, PathInits.DIRECT2);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

