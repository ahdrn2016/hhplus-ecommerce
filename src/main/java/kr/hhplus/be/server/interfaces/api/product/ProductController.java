package kr.hhplus.be.server.interfaces.api.product;

import io.swagger.v3.oas.annotations.Operation;
import kr.hhplus.be.server.domain.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping(produces = { "application/json" })
    @Operation(summary = "상품 목록 조회", description = "상품 목록을 조회합니다.", tags = { "product" })
    public ResponseEntity<Page<ProductResponse.Product>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ProductResponse.Product> response = productService.getProducts(page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/popular", produces = { "application/json" })
    @Operation(summary = "인기 상품 목록 조회", description = "인기 상품 목록을 조회합니다.", tags = { "product" })
    public ResponseEntity<List<ProductResponse.Product>> getPopularProducts() {
        List<ProductResponse.Product> response = productService.getPopularProducts();
        return ResponseEntity.ok(response);
    }
}
