package kr.hhplus.be.server.domain.point;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PointServiceConcurrencyTest {

    @Autowired
    private PointService pointService;

    @Test
    void 유저가_동시에_포인트_충전_2회_요청_시_1번만_성공한다() throws InterruptedException {
        // given
        int threads = 2;
        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        CountDownLatch latch = new CountDownLatch(threads);

        // when
        for(int i = 1; i <= threads; i++) {
            executorService.submit(() -> {
                try {
                    PointCommand.Charge command = PointCommand.Charge.builder().userId(3L).amount(10000).build();
                    pointService.charge(command);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executorService.shutdown();

        // then
        PointInfo.Point result = pointService.point(3L);
        assertEquals(10000, result.point());
    }

    @Test
    void 유저가_동시에_포인트_사용_2회_요청_시_1번만_성공한다() throws InterruptedException {
        // given
        int threads = 2;
        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        CountDownLatch latch = new CountDownLatch(threads);

        // when
        for(int i = 1; i <= threads; i++) {
            executorService.submit(() -> {
                try {
                    PointCommand.Use command = PointCommand.Use.builder().userId(4L).amount(10000).build();
                    pointService.use(command);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executorService.shutdown();

        // then
        PointInfo.Point result = pointService.point(4L);
        assertEquals(10000, result.point());
    }

}