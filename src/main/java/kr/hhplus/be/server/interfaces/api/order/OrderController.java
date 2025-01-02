package kr.hhplus.be.server.interfaces.api.order;

import kr.hhplus.be.server.interfaces.api.order.dto.CreateOrderRequest;
import kr.hhplus.be.server.interfaces.api.order.dto.OrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest request) {
        OrderResponse response = new OrderResponse();
        return ResponseEntity.ok(response);
    }

}
