package kr.hhplus.be.server.interfaces.api.coupon;

import io.swagger.v3.oas.annotations.Operation;
import kr.hhplus.be.server.domain.coupon.CouponInfo;
import kr.hhplus.be.server.domain.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/coupon")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @GetMapping(path = "/{userId}", produces = { "application/json" })
    @Operation(summary = "보유 쿠폰 조회", description = "유저의 보유 쿠폰을 조회합니다.", tags = { "coupon" })
    public ResponseEntity<List<CouponResponse.IssuedCoupon>> coupons(
            @PathVariable Long userId
    ) {
        List<CouponInfo.IssuedCoupon> response = couponService.coupons(userId);
        return ResponseEntity.ok(CouponResponse.of(response));
    }

    @PostMapping(path = "/issue", produces = { "application/json" }, consumes = { "application/json" })
    @Operation(summary = "쿠폰 발급", description = "쿠폰을 발급합니다.", tags = { "coupon" })
    public ResponseEntity<CouponResponse.IssuedCoupon> issue(
            @RequestBody CouponRequest.Issue request
    ) {
        CouponInfo.IssuedCoupon response = couponService.issue(request.toCommand());
        return ResponseEntity.ok(CouponResponse.of(response));
    }

}
