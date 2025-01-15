package kr.hhplus.be.server.domain.product;

import kr.hhplus.be.server.support.exception.CustomException;
import kr.hhplus.be.server.support.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Page<ProductInfo.Product> products(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findAllByStatus(ProductStatus.SELLING, pageable);
        return ProductInfo.of(products);
    }

    public List<ProductInfo.Product> popularProducts() {
        List<ProductInfo.PopularProduct> products = productRepository.findPopularProducts();
        return products.stream()
                .map(ProductInfo.PopularProduct::of)
                .collect(Collectors.toList());
    }

    public List<ProductInfo.Product> orderProducts(List<ProductCommand.Product> command) {
        List<Long> productIds = getProductIds(command);
        stoppedProduct(productIds);

        List<Product> products = productRepository.findAllByIdIn(productIds);
        return products.stream()
                .map(ProductInfo.Product::of)
                .collect(Collectors.toList());
    }

    public void deductStock(List<ProductCommand.Product> command) {
        List<Long> productIds = getProductIds(command);
        List<Product> products = productRepository.findAllByIdIn(productIds);

        Map<Long, Integer> OrderProductMap = createOrderProductMap(command);

        for(Product product : products) {
            int quantity = OrderProductMap.get(product.getId());
            product.deductStock(quantity);
        }
    }

    private static List<Long> getProductIds(List<ProductCommand.Product> products) {
        return products.stream()
                .map(ProductCommand.Product::productId)
                .collect(Collectors.toList());
    }

    private void stoppedProduct(List<Long> productIds) {
        boolean stoppedProduct = productRepository.existsByIdInAndStatus(productIds, ProductStatus.STOP);
        if (stoppedProduct) {
            throw new CustomException(ErrorCode.HAS_STOPPED_PRODUCT);
        }
    }

    private static Map<Long, Integer> createOrderProductMap(List<ProductCommand.Product> products) {
        return products.stream()
                .collect(Collectors.toMap(
                        ProductCommand.Product::productId,
                        ProductCommand.Product::quantity
                ));
    }
}
