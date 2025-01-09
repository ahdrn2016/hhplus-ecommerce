package kr.hhplus.be.server.domain.order;

import kr.hhplus.be.server.application.order.OrderResult;
import kr.hhplus.be.server.application.payment.PaymentResult;
import kr.hhplus.be.server.domain.coupon.CouponCommand;
import kr.hhplus.be.server.domain.payment.PaymentInfo;
import kr.hhplus.be.server.domain.product.Product;
import kr.hhplus.be.server.domain.product.ProductCommand;
import kr.hhplus.be.server.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;
    private final DataPlatformClient dataPlatformClient;

    public OrderResult.Order createOrder(Long userId, CouponCommand.OrderCoupon userCoupon, List<ProductCommand.OrderProduct> products) {
        int totalAmount = getTotalAmount(products);

        Order order = Order.create(userId, userCoupon, totalAmount);
        Order savedOrder = orderRepository.save(order);

        dataPlatformClient.send(savedOrder);

        return OrderInfo.toResult(savedOrder);
    }

    private int getTotalAmount(List<ProductCommand.OrderProduct> products) {
        int totalAmount = 0;
        List<Long> productIds = getProductIds(products);

        Map<Long, Integer> orderProductMap = createOrderProductMap(products);

        List<Product> orderProducts = productRepository.findAllByIdIn(productIds);
        for (Product orderProduct : orderProducts) {
            Long id = orderProduct.getId();
            int quantity = orderProductMap.get(id);
            totalAmount += orderProduct.sum(quantity);
        }
        return totalAmount;
    }

    public PaymentResult.Order getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId);
        return PaymentInfo.toResult(order);
    }

    private static List<Long> getProductIds(List<ProductCommand.OrderProduct> products) {
        return products.stream()
                .map(ProductCommand.OrderProduct::productId)
                .collect(Collectors.toList());
    }

    private static Map<Long, Integer> createOrderProductMap(List<ProductCommand.OrderProduct> products) {
        return products.stream()
                .collect(Collectors.toMap(
                        ProductCommand.OrderProduct::productId,
                        ProductCommand.OrderProduct::quantity
                ));
    }
}
