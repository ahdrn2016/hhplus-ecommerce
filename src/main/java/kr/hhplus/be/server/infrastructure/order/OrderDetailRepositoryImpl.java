package kr.hhplus.be.server.infrastructure.order;

import kr.hhplus.be.server.domain.order.OrderDetail;
import kr.hhplus.be.server.domain.order.OrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderDetailRepositoryImpl implements OrderDetailRepository {

    private final OrderDetailJpaRepository OrderDetailJpaRepository;

    @Override
    public OrderDetail save(OrderDetail OrderDetail) {
        return OrderDetailJpaRepository.save(OrderDetail);
    }

}
