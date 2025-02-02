package kr.hhplus.be.server.domain.order;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {
    
    @Test
    public void 주문_시_상품의_총_금액을_계산한다() {
        // given
        List<OrderProduct> orderProducts = List.of(
                OrderProduct.builder().productId(1L).price(BigDecimal.valueOf(1000)).quantity(3).build(),
                OrderProduct.builder().productId(2L).price(BigDecimal.valueOf(2000)).quantity(5).build()
        );

        // when
        Order order = Order.create(1L, orderProducts);

        // then
        assertEquals(13000, order.getTotalAmount());
    }

}