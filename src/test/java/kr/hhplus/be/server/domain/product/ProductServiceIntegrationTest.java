package kr.hhplus.be.server.domain.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceIntegrationTest {

    @Autowired
    private ProductService productService;

    @Test
    void 최근_3일간_주문량_많은_상품_5개를_조회한다() {
        // when
        List<ProductInfo.PopularProduct> products = productService.popularProducts();

        // then
        assertEquals(5, products.size());
        assertEquals("커피", products.get(0).name());
    }
}