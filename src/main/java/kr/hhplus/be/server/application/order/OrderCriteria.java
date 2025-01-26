package kr.hhplus.be.server.application.order;

import kr.hhplus.be.server.domain.coupon.CouponCommand;
import kr.hhplus.be.server.domain.order.OrderCommand;
import kr.hhplus.be.server.domain.payment.PaymentInfo;
import kr.hhplus.be.server.domain.point.PointCommand;
import kr.hhplus.be.server.domain.product.ProductCommand;
import kr.hhplus.be.server.domain.product.ProductInfo;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

public class OrderCriteria {

    public record Order(
            Long userId,
            Long couponId,
            List<OrderProduct> products
    ) {
        @Builder
        public Order {}

        public List<ProductCommand.Product> toProductCommand() {
            return products.stream()
                    .map(product -> ProductCommand.Product.builder()
                            .productId(product.productId)
                            .quantity(product.quantity)
                            .build())
                    .toList();
        }

        public OrderCommand.Order toOrderCommand(Order criteria, List<ProductInfo.Product> products) {
            List<OrderCommand.OrderProduct> orderProducts = products.stream()
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

            return OrderCommand.Order.builder()
                    .userId(criteria.userId)
                    .products(orderProducts)
                    .build();
        }

        public PointCommand.Use toPointCommand(Order criteria, PaymentInfo.Payment payment) {
            return PointCommand.Use.builder()
                    .userId(criteria.userId)
                    .amount(payment.paymentAmount())
                    .build();
        }

        public CouponCommand.Issue toCouponCommand(Order criteria, Long couponId) {
            return CouponCommand.Issue.builder()
                    .userId(criteria.userId)
                    .couponId(couponId)
                    .build();
        }
    }

    public record OrderProduct(
            Long productId,
            int quantity
    ) {
        @Builder
        public OrderProduct {}
    }
}
