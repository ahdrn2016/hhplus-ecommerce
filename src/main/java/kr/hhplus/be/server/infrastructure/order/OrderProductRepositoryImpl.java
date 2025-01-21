package kr.hhplus.be.server.infrastructure.order;

import kr.hhplus.be.server.domain.order.OrderProduct;
import kr.hhplus.be.server.domain.order.OrderProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderProductRepositoryImpl implements OrderProductRepository {

    private final OrderProductJpaRepository orderProductJpaRepository;

    @Override
    public void saveAll(List<OrderProduct> orderProducts) {
        orderProductJpaRepository.saveAll(orderProducts);
    }

}
