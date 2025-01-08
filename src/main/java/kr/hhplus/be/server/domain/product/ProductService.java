package kr.hhplus.be.server.domain.product;

import kr.hhplus.be.server.interfaces.api.product.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Page<ProductResponse.Products> getProducts(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findAllByProductStatus(ProductStatus.SELLING, pageable);
        return ProductInfo.toResponse(products);
    }

    public ProductResponse.Products getPopularProducts() {
        Product products = productRepository.findPopularProducts();
        return null;
    }
}
