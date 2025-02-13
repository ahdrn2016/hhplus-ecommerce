package kr.hhplus.be.server.domain.order;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
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
        return OrderInfo.of(savedOrder);
    }

    @Transactional
    public void complete(Long orderId) {
        Order order = orderRepository.findById(orderId);
        order.complete();
        applicationEventPublisher.publishEvent(OrderEvent.of(order));
    }

}
