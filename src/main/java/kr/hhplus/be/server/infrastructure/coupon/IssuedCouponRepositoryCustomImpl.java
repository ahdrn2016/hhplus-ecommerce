package kr.hhplus.be.server.infrastructure.coupon;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.hhplus.be.server.domain.coupon.IssuedCoupon;
import kr.hhplus.be.server.domain.coupon.IssuedCouponStatus;
import kr.hhplus.be.server.domain.coupon.QCoupon;
import kr.hhplus.be.server.domain.coupon.QIssuedCoupon;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class IssuedCouponRepositoryCustomImpl implements IssuedCouponRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public IssuedCoupon getIssuedCoupon(Long userId, Long couponId, IssuedCouponStatus status) {
        QIssuedCoupon issuedCoupon = QIssuedCoupon.issuedCoupon;
        QCoupon coupon = QCoupon.coupon;

        return queryFactory
                .selectFrom(issuedCoupon)
                .join(coupon).on(issuedCoupon.couponId.eq(coupon.id))
                .where(
                    issuedCoupon.userId.eq(userId),
                    issuedCoupon.couponId.eq(couponId),
                    issuedCoupon.status.eq(status),
                    coupon.validStartDate.loe(LocalDateTime.now()),
                    coupon.validEndDate.goe(LocalDateTime.now())
                )
                .fetchOne();
    }

}
