package kr.hhplus.be.server.application.order;

import kr.hhplus.be.server.domain.coupon.CouponCommand;
import kr.hhplus.be.server.domain.coupon.CouponInfo;
import kr.hhplus.be.server.domain.coupon.CouponService;
import kr.hhplus.be.server.domain.order.OrderCommand;
import kr.hhplus.be.server.domain.order.OrderInfo;
import kr.hhplus.be.server.domain.order.OrderService;
import kr.hhplus.be.server.domain.payment.PaymentInfo;
import kr.hhplus.be.server.domain.payment.PaymentService;
import kr.hhplus.be.server.domain.point.PointCommand;
import kr.hhplus.be.server.domain.point.PointService;
import kr.hhplus.be.server.domain.product.ProductInfo;
import kr.hhplus.be.server.domain.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderFacade {

    private final ProductService productService;
    private final OrderService orderService;
    private final CouponService couponService;
    private final PaymentService paymentService;
    private final PointService pointService;

    @Transactional
    public OrderResult.Payment order(OrderCriteria.Order criteria) {
        List<ProductInfo.Product> products = productService.orderProducts(criteria.toProductCommand());
        List<OrderCommand.OrderProduct> orderProducts = OrderCommand.createOrderProducts(criteria, products);
        OrderInfo.Order order = orderService.order(new OrderCommand.Order(criteria.userId(), orderProducts));
        CouponInfo.IssuedCoupon coupon = Optional.ofNullable(criteria.couponId())
                                        .map(couponId -> couponService.use(new CouponCommand.Issue(criteria.userId(), couponId)))
                                        .orElse(null);
        PaymentInfo.Payment payment = paymentService.payment(order.orderId(), order.totalAmount(),
                Optional.ofNullable(coupon).map(CouponInfo.IssuedCoupon::amount).orElse(0));
        pointService.use(new PointCommand.Use(criteria.userId(), payment.paymentAmount()));
        productService.deductStock(criteria.toProductCommand());
        orderService.complete(order.orderId());

        return OrderResult.of(payment);
    }

}
