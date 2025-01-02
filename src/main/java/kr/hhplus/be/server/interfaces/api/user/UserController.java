package kr.hhplus.be.server.interfaces.api.user;

import kr.hhplus.be.server.interfaces.api.user.dto.BalanceResponse;
import kr.hhplus.be.server.interfaces.api.user.dto.ChargeBalanceRequest;
import kr.hhplus.be.server.interfaces.api.user.dto.UserCouponsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @GetMapping("/balance/{userId}")
    public ResponseEntity<BalanceResponse> getBalance(@PathVariable Long userId) {
        BalanceResponse response = new BalanceResponse();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/balance/charge")
    public ResponseEntity<BalanceResponse> chargeBalance(@RequestBody ChargeBalanceRequest request) {
        BalanceResponse response = new BalanceResponse();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/coupons/{userId}")
    public ResponseEntity<UserCouponsResponse> getUserCoupons(@PathVariable Long userId) {
        UserCouponsResponse response = new UserCouponsResponse();
        return ResponseEntity.ok(response);
    }

}
