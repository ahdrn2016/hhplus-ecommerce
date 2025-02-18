package kr.hhplus.be.server.domain.coupon;

import java.util.Set;

public interface CouponRepository {

    Coupon findById(Long couponId);

    Coupon save(Coupon coupon);

    boolean findIssuedCoupon(Long couponId, Long userId);

    boolean addCouponRequest(Long couponId, Long userId);

    void setCouponQuantity(Long couponId, int quantity);

    int getCouponQuantity(Long couponId);

    void decrementCouponQuantity(Long couponId);

    Set<Long> getCouponIds();

    Set<Long> getUserIds(Long couponId, int batchSize);

    void setIssuedCoupon(Long couponId, Long userId);

    Long findIssuedCouponCount(Long couponId);

    void deleteCouponIssue(Long couponId);
}
