package kr.hhplus.be.server.application.order;

import kr.hhplus.be.server.domain.coupon.CouponService;
import kr.hhplus.be.server.domain.product.ProductService;
import kr.hhplus.be.server.domain.user.UserService;
import kr.hhplus.be.server.interfaces.api.order.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderFacade {

    private final CouponService couponService;
    private final ProductService productService;
    private final UserService userService;

    public OrderResponse.Order createOrder(OrderParam.CreateOrder param) {
        Long userId = param.userId();
        Long couponId = param.couponId();
        List<OrderParam.OrderProduct> products = param.products();

        // 쿠폰 -> 쿠폰 확인
//        couponService.getCoupon(param.couponId());

        // 상품 -> 판매 상태, 재고 확인, 재고 차감

        // 재고 차감, 결제 금액 계산

        // 결제 -> 금액 조회, 금액 차감, 결제 기록

        return null;
    }
}
