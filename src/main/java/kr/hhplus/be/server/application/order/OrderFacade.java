package kr.hhplus.be.server.application.order;

import kr.hhplus.be.server.domain.coupon.CouponService;
import kr.hhplus.be.server.domain.order.OrderService;
import kr.hhplus.be.server.domain.product.ProductCommand;
import kr.hhplus.be.server.domain.product.ProductService;
import kr.hhplus.be.server.domain.payment.PaymentService;
import kr.hhplus.be.server.domain.user.UserService;
import kr.hhplus.be.server.interfaces.api.coupon.CouponResponse;
import kr.hhplus.be.server.interfaces.api.order.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderFacade {

    private final CouponService couponService;
    private final ProductService productService;
    private final OrderService orderService;
    private final UserService userService;

    public OrderResponse.Order createOrder(OrderParam.CreateOrder param) {
        List<ProductCommand.OrderProduct> products = param.products().stream()
                .map(OrderParam.OrderProduct::toCommand)
                .toList();

        CouponResponse.Coupon userCoupon = null;
        // 쿠폰 사용
        if (param.couponId() != null) {
            userCoupon = couponService.useCoupon(param.userId(), param.couponId());
        }

        // 재고 차감
        productService.deductStock(products);

        // 주문 생성
        OrderResult.Order order = orderService.createOrder(param.userId(), userCoupon.toCommand(), products);

        return OrderResult.toResponse(order);
    }
}
