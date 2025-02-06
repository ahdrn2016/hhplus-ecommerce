package kr.hhplus.be.server.interfaces.scheduler;

import kr.hhplus.be.server.domain.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductScheduler {

    private final ProductService productService;

    @Scheduled(cron = "0 0 0 * * *")
    @CachePut("popular_products")
    public void cachePopularProducts() {
        productService.popularProducts();
    }
}
