package kr.hhplus.be.server.support.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    INVALID_CHARGE_AMOUNT(HttpStatus.BAD_REQUEST, "0원 이상부터 충전 가능합니다."),
    INSUFFICIENT_BALANCE(HttpStatus.BAD_REQUEST, "잔액이 부족합니다."),

    DUPLICATE_ISSUE_COUPON(HttpStatus.BAD_REQUEST, "이미 발급된 쿠폰입니다."),
    SOLD_OUT_COUPON(HttpStatus.BAD_REQUEST, "쿠폰이 모두 소진되었습니다."),
    ALREADY_USED_COUPON(HttpStatus.BAD_REQUEST, "이미 사용된 쿠폰입니다."),

    HAS_STOPPED_PRODUCT(HttpStatus.BAD_REQUEST, "판매 중단된 상품이 포함되어 있습니다."),
    SOLD_OUT_PRODUCT(HttpStatus.BAD_REQUEST, "상품의 재고가 부족합니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
