package kr.hhplus.be.server.interfaces.api.coupon;

import kr.hhplus.be.server.interfaces.api.coupon.dto.CouponRequest;
import kr.hhplus.be.server.interfaces.api.coupon.dto.CouponResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/coupon")
public class CouponController {

    @PostMapping("/issue")
    public ResponseEntity<CouponResponse> issueCoupon(@RequestBody CouponRequest request) {
        CouponResponse response = new CouponResponse();
        return ResponseEntity.ok(response);
    }

}
