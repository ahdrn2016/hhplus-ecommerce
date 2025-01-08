package kr.hhplus.be.server.interfaces.api.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import kr.hhplus.be.server.domain.user.UserService;
import kr.hhplus.be.server.domain.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final CouponService couponService;

    @GetMapping(path="/balance/{userId}", produces = { "application/json" })
    @Operation(summary = "잔액 조회", description = "유저의 잔액을 조회합니다.", tags = { "users" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "잔액 조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.Balance.class))),
            @ApiResponse(responseCode = "400", description = "요청 오류") })
    public ResponseEntity<UserResponse.Balance> getBalance(
            @PathVariable Long userId
    ) {
        UserResponse.Balance response = userService.getBalance(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping(path="/balance/charge", produces = { "application/json" }, consumes = { "application/json" })
    @Operation(summary = "잔액 충전", description = "유저의 잔액을 충전합니다.", tags = { "users" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "잔액 충전 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.Balance.class))),
            @ApiResponse(responseCode = "400", description = "요청 오류") })
    public ResponseEntity<UserResponse.Balance> chargeBalance(
            @RequestBody UserRequest.ChargeBalance request
    ) {
        UserResponse.Balance response = userService.chargeBalance(request.toCommand());
        return ResponseEntity.ok(response);
    }

    @GetMapping(path="/coupons/{userId}", produces = { "application/json" })
    @Operation(summary = "보유 쿠폰 조회", description = "유저의 보유 쿠폰을 조회합니다.", tags = { "users" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "보유 쿠폰 조회 성공", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserResponse.UserCoupon.class)))),
            @ApiResponse(responseCode = "400", description = "요청 오류")})
    public ResponseEntity<List<UserResponse.UserCoupon>> getCoupons(
            @PathVariable Long userId
    ) {
        List<UserResponse.UserCoupon> response = couponService.getCoupons(userId);
        return ResponseEntity.ok(response);
    }

}
