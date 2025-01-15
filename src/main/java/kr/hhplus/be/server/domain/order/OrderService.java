package kr.hhplus.be.server.domain.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final DataPlatformClient dataPlatformClient;

    public OrderInfo.Order order(OrderCommand.Order command) {
        List<OrderProduct> orderProducts = command.products().stream()
                .map(orderProduct -> OrderProduct.builder()
                        .productId(orderProduct.productId())
                        .price(orderProduct.price())
                        .quantity(orderProduct.quantity())
                        .build())
                .toList();

        Order order = Order.create(command.userId(), orderProducts);
        Order savedOrder = orderRepository.save(order);

        createOrderProducts(orderProducts, savedOrder);

        return OrderInfo.of(savedOrder);
    }

    private void createOrderProducts(List<OrderProduct> orderProducts, Order savedOrder) {
        List<OrderProduct> savedOrderProducts = orderProducts.stream()
                .map(product -> OrderProduct.builder()
                        .order(savedOrder)
                        .productId(product.getProductId())
                        .price(product.getPrice())
                        .quantity(product.getQuantity())
                        .build()
                )
                .toList();
        orderProductRepository.saveAll(savedOrderProducts);
    }

    public void complete(Long orderId) {
        Order order = orderRepository.findById(orderId);
        order.complete();
        dataPlatformClient.sendData(order);
    }

}
