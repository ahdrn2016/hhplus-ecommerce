package kr.hhplus.be.server.domain.order;

import kr.hhplus.be.server.domain.product.ProductCommand;
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

    public OrderInfo.OrderDto createOrder(Long userId, int totalAmount, int discountAmount, List<ProductCommand.OrderProduct> productDtos) {
        Order order = Order.create(userId, discountAmount, totalAmount);
        Order savedOrder = orderRepository.save(order);

        createOrderProduct(productDtos, savedOrder);

        return OrderInfo.of(savedOrder);
    }

    private void createOrderProduct(List<ProductCommand.OrderProduct> productDtos, Order savedOrder) {
        Map<Long, Integer> orderProductMap = productDtos.stream()
                .collect(Collectors.toMap(
                        ProductCommand.OrderProduct::productId,
                        ProductCommand.OrderProduct::quantity
                ));

        orderProductMap.forEach((productId, quantity) -> {
            OrderProduct orderProduct = new OrderProduct(savedOrder.getId(), productId, quantity);
            orderProductRepository.save(orderProduct);
        });
    }

}
