package kr.hhplus.be.server.interfaces.api.coupon;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import kr.hhplus.be.server.domain.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/coupon")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping(path = "/issue", produces = { "application/json" }, consumes = { "application/json" })
    @Operation(summary = "쿠폰 발급", description = "쿠폰을 발급합니다.", tags = { "users" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "쿠폰 발급 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CouponResponse.issueCoupon.class))),
            @ApiResponse(responseCode = "400", description = "요청 오류") })
    public ResponseEntity<CouponResponse.issueCoupon> issueCoupon(@RequestBody CouponRequest.issueCoupon request) {
        CouponResponse.issueCoupon response = couponService.issueCoupon(request.toCommand());
        return ResponseEntity.ok(response);
    }

}
