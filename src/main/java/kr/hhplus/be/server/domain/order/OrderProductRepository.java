package kr.hhplus.be.server.domain.order;

import java.util.List;

public interface OrderProductRepository {

    void saveAll(List<OrderProduct> orderProducts);

}
