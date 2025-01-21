package kr.hhplus.be.server.domain.coupon;

public interface CouponRepository {

    Coupon findByIdWithLock(Long couponId);

    Coupon save(Coupon coupon);

    Coupon findById(Long couponId);
}
