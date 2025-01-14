package kr.hhplus.be.server.domain.product;

import kr.hhplus.be.server.support.exception.CustomException;
import kr.hhplus.be.server.support.exception.ErrorCode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void 재고가_부족하면_예외가_발생한다() {
        // given
        Product product = new Product(1L, "테스트 상품", 1000, ProductStatus.SELLING, 10);

        // when // then
        assertThatThrownBy(() -> product.deductStock(20))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.SOLD_OUT_PRODUCT.getMessage());
    }

    @Test
    void 충분한_재고가_있으면_재고를_차감한다() {
        // given
        Product product = new Product(1L, "테스트 상품", 1000, ProductStatus.SELLING, 10);

        // when
        product.deductStock(5);

        // then
        assertThat(product.getStock()).isEqualTo(5);
    }

    @Test
    void 상품_가격과_수량을_곱한다() {
        // given
        Product product = new Product(1L, "테스트 상품", 1000, ProductStatus.SELLING, 10);

        // when
        int total = product.sum(3);

        // then
        assertThat(total).isEqualTo(3000);
    }

}