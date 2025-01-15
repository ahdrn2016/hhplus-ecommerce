package kr.hhplus.be.server.interfaces.api.order;

import io.swagger.v3.oas.annotations.Operation;
import kr.hhplus.be.server.application.order.OrderFacade;
import kr.hhplus.be.server.application.order.OrderResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderFacade orderFacade;

    @PostMapping(produces = { "application/json" }, consumes = { "application/json" })
    @Operation(summary = "주문", description = "주문을 진행합니다.", tags = { "order" })
    public ResponseEntity<OrderResponse.Order> order(
            @RequestBody OrderRequest.Order request
    ) {
        OrderResult.Order response = orderFacade.order(request.toCriteria());
        return ResponseEntity.ok(OrderResponse.of(response));
    }

}
