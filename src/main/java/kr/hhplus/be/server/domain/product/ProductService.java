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

    public Page<ProductInfo.ProductDto> getProducts(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findAllByStatus(ProductStatus.SELLING, pageable);
        return ProductInfo.of(products);
    }

    public List<ProductInfo.ProductDto> getPopularProducts() {
        List<ProductInfo.PopularProductDto> products = productRepository.findPopularProducts();
        return ProductInfo.of(products);
    }

    public int deductStockAndCalculateTotal(List<ProductCommand.OrderProduct> productDtos) {
        int totalAmount = 0;

        List<Long> productIds = getProductIds(productDtos);

        // 판매 중단 체크
        stoppedProduct(productIds);

        Map<Long, Integer> orderProductMap = createOrderProductMap(productDtos);

        // 재고 확인 후 차감
        List<Product> products = productRepository.findAllByIdIn(productIds);
        for(Product product : products) {
            int price = product.getPrice();
            int quantity = orderProductMap.get(product.getId());

            totalAmount += price * quantity;

            product.deductStock(quantity);
        }

        return totalAmount;
    }

    private static List<Long> getProductIds(List<ProductCommand.OrderProduct> products) {
        return products.stream()
                .map(ProductCommand.OrderProduct::productId)
                .collect(Collectors.toList());
    }

    private void stoppedProduct(List<Long> productIds) {
        boolean stoppedProduct = productRepository.existsByIdInAndStatus(productIds, ProductStatus.STOP);
        if (stoppedProduct) {
            throw new CustomException(ErrorCode.HAS_STOPPED_PRODUCT);
        }
    }

    private static Map<Long, Integer> createOrderProductMap(List<ProductCommand.OrderProduct> products) {
        return products.stream()
                .collect(Collectors.toMap(
                        ProductCommand.OrderProduct::productId,
                        ProductCommand.OrderProduct::quantity
                ));
    }
}
