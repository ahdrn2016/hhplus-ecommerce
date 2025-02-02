package kr.hhplus.be.server.domain.product;

import kr.hhplus.be.server.support.exception.CustomException;
import kr.hhplus.be.server.support.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
                new Product(1L, "상품 A", BigDecimal.valueOf(1000), ProductStatus.SELLING, 10),
                new Product(2L, "상품 B", BigDecimal.valueOf(2000), ProductStatus.SELLING, 20)
        );
        Page<Product> productPage = new PageImpl<>(productList, pageable, 10);

        given(productRepository.findAllByStatus(any(), eq(pageable))).willReturn(productPage);

        // when
        Page<ProductInfo.Product> result = productService.products(page, size);

        // then
        assertNotNull(result);
        assertEquals(0, result.getNumber());
        assertEquals(5, result.getTotalPages());
    }

    @Test
    void 주문_시_주문_상품_중_판매_중단된_상품이_있으면_예외가_발생한다() {
        // given
        List<ProductCommand.Product> command = List.of(
                new ProductCommand.Product(1L, 2),
                new ProductCommand.Product(2L, 3)
        );
        List<Long> productIds = List.of(1L, 2L);

        given(productRepository.existsByIdInAndStatus(productIds, ProductStatus.STOP)).willReturn(true);

        // when // then
        assertThatThrownBy(() -> productService.orderProducts(command))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.HAS_STOPPED_PRODUCT.getMessage());
    }

    @Test
    void 주문_시_재고가_부족한_상품이_있으면_예외가_발생한다() {
        // given
        List<ProductCommand.Product> command = List.of(
                new ProductCommand.Product(1L, 2),
                new ProductCommand.Product(2L, 3)
        );
        List<Long> productIds = List.of(1L, 2L);
        List<Product> products = List.of(
                new Product(1L, "Product A", BigDecimal.valueOf(1000), ProductStatus.SELLING, 2),
                new Product(2L, "Product B", BigDecimal.valueOf(2000), ProductStatus.SELLING, 2)    // 2번 상품 재고 부족
        );

        given(productRepository.findAllByIdInWithLock(productIds)).willReturn(products);

        // when // then
        assertThatThrownBy(() -> productService.deductStock(command))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.SOLD_OUT_PRODUCT.getMessage());
    }

}