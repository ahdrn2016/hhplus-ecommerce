package kr.hhplus.be.server.domain.product;

import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.coupon.CouponCommand;
import kr.hhplus.be.server.support.exception.CustomException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceConcurrencyTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void 재고_5개_남은_상품을_동시에_재고차감_5번_요청_시_요청에_성공한다() throws InterruptedException {
        // given
        int threads = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        CountDownLatch latch = new CountDownLatch(threads);

        // when
        for(int i = 1; i <= threads; i++) {
            executorService.submit(() -> {
                try {
                    List<ProductCommand.Product> command = List.of(
                            ProductCommand.Product.builder().productId(1L).quantity(1).build()
                    );
                    productService.deductStock(command);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executorService.shutdown();

        //then
        Product result = productRepository.findById(1L);
        assertEquals(0, result.getStock());
    }

    @Test
    void 재고_5개_남은_상품을_동시에_재고차감_10번_요청_시_5번은_실패한다() throws InterruptedException {
        // given
        int threads = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        CountDownLatch latch = new CountDownLatch(threads);

        AtomicInteger failCount = new AtomicInteger(0);

        // when
        for(int i = 1; i <= threads; i++) {
            executorService.submit(() -> {
                try {
                    List<ProductCommand.Product> command = List.of(
                            ProductCommand.Product.builder().productId(1L).quantity(1).build()
                    );
                    productService.deductStock(command);
                } catch (CustomException e) {
                    failCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executorService.shutdown();

        //then
        Product result = productRepository.findById(1L);
        assertEquals(0, result.getStock());
        assertEquals(5, failCount.get());
    }
    
}