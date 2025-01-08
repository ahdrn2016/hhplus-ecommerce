package kr.hhplus.be.server.interfaces.api.coupon;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @GetMapping(path = "/coupons/{userId}", produces = { "application/json" })
    @Operation(summary = "보유 쿠폰 조회", description = "유저의 보유 쿠폰을 조회합니다.", tags = { "coupon" })
    public ResponseEntity<List<CouponResponse.Coupons>> getCoupons(
            @PathVariable Long userId
    ) {
        List<CouponResponse.Coupons> response = couponService.getCoupons(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/issue", produces = { "application/json" }, consumes = { "application/json" })
    @Operation(summary = "쿠폰 발급", description = "쿠폰을 발급합니다.", tags = { "coupon" })
    public ResponseEntity<CouponResponse.issueCoupon> issueCoupon(@RequestBody CouponRequest.issueCoupon request) {
        CouponResponse.issueCoupon response = couponService.issueCoupon(request.toCommand());
        return ResponseEntity.ok(response);
    }

}
