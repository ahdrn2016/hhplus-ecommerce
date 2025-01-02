package kr.hhplus.be.server.interfaces.api.order.dto;

import java.util.List;

public class CreateOrderRequest {

    private Long userId;
    private Long couponId;
    private List<OrderProductInfo> products;

}
