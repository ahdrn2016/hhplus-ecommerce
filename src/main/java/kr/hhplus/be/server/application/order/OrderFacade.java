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
import kr.hhplus.be.server.domain.product.ProductCommand;
import kr.hhplus.be.server.domain.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
//        List<ProductCommand.OrderDetail> productDtos = criteria.products().stream()
//                .map(OrderCriteria.OrderDetail::toCommand)
//                .toList();

        /**
         * List<Products> products = productService.getProducts(new ProductCommand.GetProducts(...));
         *            OrderInfo.Order order = orderService.order(new OrderCommand.Order(...));
         *
         * 				   Coupon? coupon = criteria?.couponId?.let { couponService::getCoupon}
         *
         * //         CouponInfo.Coupon coupon = couponService.getCoupon(criteria.userId(), criteria.couponId());
         * 	         paymentService.payment(order.orderId(), order.paymentAmount(), coupon?.할인가);
         *            CouponInfo.Coupon coupon = couponService.useCoupon(criteria.userId(), criteria.couponId());
         *  	         userService.useBalance(new UserCommand.UseBalance(cri.userId, order.totalAmount));
         * 		       productService.deductStock(new ProductCommand.deductStock(...));
         * 		       orderService.complte(new OrderCommand.Compelete(...));
         */

        int totalAmount = 0;

        OrderInfo.Order order = orderService.order(new OrderCommand.Order(criteria.userId(), totalAmount));
        CouponInfo.IssuedCoupon coupon = criteria.couponId() != null ?
                couponService.getCoupon(new CouponCommand.Issue(criteria.userId(), criteria.couponId())) : null;
        paymentService.payment(order.orderId(), order.paymentAmount(), coupon != null ? coupon.amount() : 0);
        pointService.use(new PointCommand.Use(order.orderId(), order.paymentAmount()));


        return null;
    }
}
