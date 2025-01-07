package kr.hhplus.be.server.interfaces.api.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import kr.hhplus.be.server.interfaces.api.product.dto.ProductsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @GetMapping(produces = { "application/json" })
    @Operation(summary = "상품 목록 조회", description = "상품 목록을 조회합니다.", tags = { "users" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 목록 조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductsResponse.class))),
            @ApiResponse(responseCode = "400", description = "요청 오류") })
    public ResponseEntity<ProductsResponse> getProducts() {
        ProductsResponse response = new ProductsResponse();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path="/popular", produces = { "application/json" })
    @Operation(summary = "인기 상품 목록 조회", description = "인기 상품 목록을 조회합니다.", tags = { "users" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인기 상품 목록 조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductsResponse.class))),
            @ApiResponse(responseCode = "400", description = "요청 오류") })
    public ResponseEntity<ProductsResponse> getPopularProducts() {
        ProductsResponse response = new ProductsResponse();
        return ResponseEntity.ok(response);
    }
}
