package kr.hhplus.be.server.infrastructure.product;

import kr.hhplus.be.server.domain.product.ProductInfo;

import java.util.List;

public interface ProductRepositoryCustom {

    List<ProductInfo.PopularProduct> getPopularProducts();

}
