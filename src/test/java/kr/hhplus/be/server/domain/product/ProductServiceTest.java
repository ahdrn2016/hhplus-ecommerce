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
                new Product(1L, "상품 A", 1000, ProductStatus.SELLING, 10),
                new Product(2L, "상품 B", 2000, ProductStatus.SELLING, 20)
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
    void 주문_시_판매_중단된_상품이_있으면_예외가_발생한다() {
        // given
        List<ProductCommand.OrderProduct> productDtos = List.of(
                new ProductCommand.OrderProduct(1L, 2),
                new ProductCommand.OrderProduct(2L, 3)
        );

        List<Long> productIds = List.of(1L, 2L);

        given(productRepository.existsByIdInAndStatus(productIds, ProductStatus.STOP)).willReturn(true);
        
        // when // then
        assertThatThrownBy(() -> productService.deductStockAndCalculateTotal(productDtos))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.HAS_STOPPED_PRODUCT.getMessage());
    }

    @Test
    void 주문_시_재고가_부족한_상품이_있으면_예외가_발생한다() {
        // given
        List<ProductCommand.OrderProduct> productDtos = List.of(
                new ProductCommand.OrderProduct(1L, 2),
                new ProductCommand.OrderProduct(2L, 3)
        );

        List<Long> productIds = List.of(1L, 2L);

        List<Product> products = List.of(
                new Product(1L, "Product A", 1000, ProductStatus.SELLING, 2),
                new Product(2L, "Product B", 2000, ProductStatus.SELLING, 2)    // 2번 상품 재고 부족
        );

        given(productRepository.findAllByIdIn(productIds)).willReturn(products);

        // when // then
        assertThatThrownBy(() -> productService.deductStockAndCalculateTotal(productDtos))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.SOLD_OUT_PRODUCT.getMessage());
    }

    @Test
    void 주문_시_주문_상품의_합계를_계산한다() {
        // given
        List<ProductCommand.OrderProduct> productDtos = List.of(
                new ProductCommand.OrderProduct(1L, 2), // 2 * 1000 = 2000
                new ProductCommand.OrderProduct(2L, 3)  // 3 * 2000 = 6000
        );

        List<Long> productIds = List.of(1L, 2L);

        List<Product> products = List.of(
                new Product(1L, "Product A", 1000, ProductStatus.SELLING, 10),
                new Product(2L, "Product B", 2000, ProductStatus.SELLING, 20)
        );

        given(productRepository.findAllByIdIn(productIds)).willReturn(products);

        // when
        int totalAmount = productService.deductStockAndCalculateTotal(productDtos);

        // then
        assertEquals(8000, totalAmount); // 합계 확인
        assertEquals(8, products.get(0).getStock()); // 첫 번째 상품의 재고 차감 확인
        assertEquals(17, products.get(1).getStock()); // 두 번째 상품의 재고 차감 확인
    }

}