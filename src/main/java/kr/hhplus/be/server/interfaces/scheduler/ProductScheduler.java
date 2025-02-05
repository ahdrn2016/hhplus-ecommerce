package kr.hhplus.be.server.interfaces.scheduler;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProductScheduler {

    @Scheduled(cron = "0 0 0 * * *")
    @CacheEvict("popular_products")
    public void evictPopularProductsCache() {}
}
