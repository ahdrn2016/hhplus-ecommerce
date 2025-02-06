package kr.hhplus.be.server.domain.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ProductServiceIntegrationTest {

    @MockitoSpyBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CacheManager cacheManager;

    @Test
    void 최근_3일간_주문량_많은_상품_5개를_조회한다() {
        // when
        List<ProductInfo.PopularProduct> products = productService.popularProducts();

        // then
        assertEquals(5, products.size());
        assertEquals("커피", products.get(0).name());
    }

    @Test
    void 인기_상품_조회에_캐싱_적용하여_메서드를_한_번만_호출한다() {
        // given
        cacheManager.getCache("popular_products").clear();

        // when
        productService.popularProducts();
        productService.popularProducts();

        // then
        verify(productRepository, times(1)).findPopularProducts();
    }

}