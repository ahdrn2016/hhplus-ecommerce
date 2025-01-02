package kr.hhplus.be.server.interfaces.api.product;

import kr.hhplus.be.server.interfaces.api.product.dto.ProductsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @GetMapping
    public ResponseEntity<ProductsResponse> getProducts() {
        ProductsResponse response = new ProductsResponse();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/popular")
    public ResponseEntity<ProductsResponse> getPopularProducts() {
        ProductsResponse response = new ProductsResponse();
        return ResponseEntity.ok(response);
    }
}
