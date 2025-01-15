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
        return ProductInfo.of(products);
    }

    public void getProducts(List<ProductCommand.Product> command) {

    }

//    public int deductStockAndCalculateTotal(List<ProductCommand.OrderDetail> productDtos) {
//        int totalAmount = 0;
//
//        List<Long> productIds = getProductIds(productDtos);
//
//        // 판매 중단 체크
//        stoppedProduct(productIds);
//
//        Map<Long, Integer> OrderDetailMap = createOrderDetailMap(productDtos);
//
//        // 재고 확인 후 차감
//        List<Product> products = productRepository.findAllByIdIn(productIds);
//        for(Product product : products) {
//            int price = product.getPrice();
//            int quantity = OrderDetailMap.get(product.getId());
//
//            totalAmount += price * quantity;
//
//            product.deductStock(quantity);
//        }
//
//        return totalAmount;
//    }
//
//    private static List<Long> getProductIds(List<ProductCommand.OrderDetail> products) {
//        return products.stream()
//                .map(ProductCommand.OrderDetail::productId)
//                .collect(Collectors.toList());
//    }
//
//    private void stoppedProduct(List<Long> productIds) {
//        boolean stoppedProduct = productRepository.existsByIdInAndStatus(productIds, ProductStatus.STOP);
//        if (stoppedProduct) {
//            throw new CustomException(ErrorCode.HAS_STOPPED_PRODUCT);
//        }
//    }
//
//    private static Map<Long, Integer> createOrderDetailMap(List<ProductCommand.OrderDetail> products) {
//        return products.stream()
//                .collect(Collectors.toMap(
//                        ProductCommand.OrderDetail::productId,
//                        ProductCommand.OrderDetail::quantity
//                ));
//    }
}
