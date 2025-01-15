package kr.hhplus.be.server.application.order;

import kr.hhplus.be.server.domain.coupon.CouponCommand;
import kr.hhplus.be.server.domain.coupon.CouponInfo;
import kr.hhplus.be.server.domain.coupon.CouponService;
import kr.hhplus.be.server.domain.order.OrderCommand;
import kr.hhplus.be.server.domain.order.OrderInfo;
import kr.hhplus.be.server.domain.order.OrderService;
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
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderFacade {

    private final ProductService productService;
    private final OrderService orderService;
    private final CouponService couponService;
    private final PaymentService paymentService;
    private final PointService pointService;

    @Transactional
    public OrderResult.Order order(OrderCriteria.Order criteria) {
        List<ProductInfo.Product> products = productService.orderProducts(criteria.toProductCommand());
        List<OrderCommand.OrderProduct> orderProducts = createOrderCommand(criteria, products);
        OrderInfo.Order order = orderService.order(new OrderCommand.Order(criteria.userId(), orderProducts));
        CouponInfo.IssuedCoupon coupon = Optional.ofNullable(criteria.couponId())
                                        .map(couponId -> couponService.use(new CouponCommand.Issue(criteria.userId(), couponId)))
                                        .orElse(null);
        paymentService.payment(order.orderId(), order.totalAmount(),
                                Optional.ofNullable(coupon).map(CouponInfo.IssuedCoupon::amount).orElse(0));
        pointService.use(new PointCommand.Use(order.orderId(), order.totalAmount()));
        productService.deductStock(criteria.toProductCommand());
        orderService.complete(order.orderId());

        return OrderResult.of(order);
    }

    private static List<OrderCommand.OrderProduct> createOrderCommand(OrderCriteria.Order criteria, List<ProductInfo.Product> products) {
        return products.stream()
                .map(product -> {
                    Long productId = product.productId();
                    int price = product.price();
                    int quantity = criteria.products().stream()
                            .filter(p -> p.productId().equals(productId))
                            .findFirst()
                            .map(OrderCriteria.OrderProduct::quantity)
                            .orElse(0);
                    return new OrderCommand.OrderProduct(productId, price, quantity);
                })
                .collect(Collectors.toList());
    }
}
