package kr.hhplus.be.server.application.order;

import kr.hhplus.be.server.domain.outbox.OutboxRepository;
import kr.hhplus.be.server.domain.outbox.OutboxStatus;
import kr.hhplus.be.server.support.exception.CustomException;
import kr.hhplus.be.server.support.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderFacadeIntegrationTest {

    @Autowired
    private OrderFacade orderFacade;

    @Autowired
    private OutboxRepository outboxRepository;

    @Test
    void 주문_시_판매_중단_상품이_있으면_예외가_발생한다() {
        // given
        List<OrderCriteria.OrderProduct> products = List.of(
                OrderCriteria.OrderProduct.builder().productId(1L).quantity(1).build(),
                OrderCriteria.OrderProduct.builder().productId(2L).quantity(2).build(),
                OrderCriteria.OrderProduct.builder().productId(3L).quantity(1).build()
        );

        OrderCriteria.Order criteria = OrderCriteria.Order.builder()
                .userId(1L)
                .products(products)
                .build();

        // when // then
        assertThatThrownBy(() -> orderFacade.order(criteria))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.HAS_STOPPED_PRODUCT.getMessage());
    }

    @Test
    void 쿠폰_사용_주문_시_유저가_이미_쿠폰을_사용했으면_예외가_발생한다() {
        // given
        List<OrderCriteria.OrderProduct> products = List.of(
                OrderCriteria.OrderProduct.builder().productId(1L).quantity(1).build(),
                OrderCriteria.OrderProduct.builder().productId(2L).quantity(2).build()
        );

        OrderCriteria.Order criteria = OrderCriteria.Order.builder()
                .userId(1L)
                .issuedCouponId(1L)
                .products(products)
                .build();

        // when // then
        assertThatThrownBy(() -> orderFacade.order(criteria))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.NO_AVAILABLE_COUPON.getMessage());
    }

    @Test
    void 주문_시_유저의_포인트가_부족하면_예외가_발생한다() {
        // given
        List<OrderCriteria.OrderProduct> products = List.of(
                OrderCriteria.OrderProduct.builder().productId(1L).quantity(1).build(),
                OrderCriteria.OrderProduct.builder().productId(2L).quantity(2).build()
        );

        OrderCriteria.Order criteria = OrderCriteria.Order.builder()
                .userId(1L)
                .issuedCouponId(2L)
                .products(products)
                .build();

        // when // then
        assertThatThrownBy(() -> orderFacade.order(criteria))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.INSUFFICIENT_POINT.getMessage());
    }

    @Test
    void 주문_시_상품의_재고가_부족하면_예외가_발생한다() {
        // given
        List<OrderCriteria.OrderProduct> products = List.of(
                OrderCriteria.OrderProduct.builder().productId(1L).quantity(1).build(),
                OrderCriteria.OrderProduct.builder().productId(2L).quantity(4).build()
        );

        OrderCriteria.Order criteria = OrderCriteria.Order.builder()
                .userId(2L)
                .issuedCouponId(2L)
                .products(products)
                .build();

        // when // then
        assertThatThrownBy(() -> orderFacade.order(criteria))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.SOLD_OUT_PRODUCT.getMessage());
    }

    @Test
    void 주문_시_주문_성공한다() {
        // given
        List<OrderCriteria.OrderProduct> products = List.of(
                OrderCriteria.OrderProduct.builder().productId(4L).quantity(1).build(),
                OrderCriteria.OrderProduct.builder().productId(5L).quantity(2).build()
        );

        OrderCriteria.Order criteria = OrderCriteria.Order.builder()
                .userId(2L)
                .issuedCouponId(3L)
                .products(products)
                .build();

        // when
        OrderResult.Payment payment = orderFacade.order(criteria);

        // then
        assertEquals(0, BigDecimal.valueOf(30000).compareTo(payment.paymentAmount()));

        await().atMost(Duration.ofSeconds(2)).untilAsserted(() ->
                assertThat(outboxRepository.findAllByStatus(OutboxStatus.SUCCESS))
                        .hasSize(1)
                        .extracting("eventType")
                        .contains("order")
        );
    }

}