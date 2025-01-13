package kr.hhplus.be.server.domain.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void 상품_목록을_페이징_처리하여_조회한다() {
        // given
        int page = 0;
        int size = 2;
        PageRequest pageable = PageRequest.of(page, size);

        List<Product> productList = List.of(
                new Product(1L, "상품 A", 1000, ProductStatus.SELLING, 10),
                new Product(2L, "상품 B", 2000, ProductStatus.SELLING, 20)
        );
        Page<Product> productPage = new PageImpl<>(productList, pageable, 10);

        given(productRepository.findAllByStatus(any(), eq(pageable))).willReturn(productPage);

        // when
        Page<ProductInfo.ProductDto> result = productService.getProducts(page, size);

        // then
        assertNotNull(result);
        assertEquals(0, result.getNumber());
        assertEquals(5, result.getTotalPages());
    }

}