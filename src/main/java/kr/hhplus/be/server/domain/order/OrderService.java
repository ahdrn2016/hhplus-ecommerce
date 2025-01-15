package kr.hhplus.be.server.domain.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;


    public OrderInfo.Order order(OrderCommand.Order order) {

        return null;
    }
}
