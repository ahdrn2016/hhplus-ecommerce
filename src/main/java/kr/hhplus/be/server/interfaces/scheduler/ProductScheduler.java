package kr.hhplus.be.server.interfaces.scheduler;

import kr.hhplus.be.server.domain.product.ProductInfo;
import kr.hhplus.be.server.domain.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductScheduler {

    private final ProductService productService;

    @Scheduled(cron = "0 0 0 * * *")
    public void cachePopularProducts() {
        updatePopularProducts();
    }

    @CachePut("popular_products")
    public List<ProductInfo.PopularProduct> updatePopularProducts() {
        return productService.popularProducts();
    }

}
