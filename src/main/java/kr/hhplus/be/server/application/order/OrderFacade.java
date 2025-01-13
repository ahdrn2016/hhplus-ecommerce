package kr.hhplus.be.server.application.order;

import kr.hhplus.be.server.domain.coupon.CouponInfo;
import kr.hhplus.be.server.domain.coupon.CouponService;
import kr.hhplus.be.server.domain.order.OrderInfo;
import kr.hhplus.be.server.domain.order.OrderService;
import kr.hhplus.be.server.domain.payment.PaymentService;
import kr.hhplus.be.server.domain.product.ProductCommand;
import kr.hhplus.be.server.domain.product.ProductService;
import kr.hhplus.be.server.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderFacade {

    private final CouponService couponService;
    private final ProductService productService;
    private final OrderService orderService;
    private final UserService userService;
    private final PaymentService paymentService;

    @Transactional
    public OrderResult.OrderDto createOrder(OrderCriteria.CreateOrder criteria) {
        List<ProductCommand.OrderProduct> productDtos = criteria.products().stream()
                .map(OrderCriteria.OrderProduct::toCommand)
                .toList();

        CouponInfo.UserCouponDto userCouponDto = null;
        // 쿠폰 사용
        if (criteria.couponId() != null) {
            userCouponDto = couponService.useCoupon(criteria.userId(), criteria.couponId());
        }

        // 재고 차감, 총 금액 계산
        int totalAmount = productService.deductStockAndCalculateTotal(productDtos);

        // 주문 생성
        OrderInfo.OrderDto order = orderService.createOrder(criteria.userId(), userCouponDto, totalAmount, productDtos);

        // 잔액 사용
        userService.useBalance(criteria.userId(), order.paymentAmount());

        // 결제 생성
        paymentService.payment(order.orderId(), order.paymentAmount());

        return OrderResult.of(order);
    }
}
