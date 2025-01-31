package kr.hhplus.be.server.domain.product;

import jakarta.persistence.*;
import kr.hhplus.be.server.support.exception.CustomException;
import kr.hhplus.be.server.support.exception.ErrorCode;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    private int stock;

    @Builder
    public Product(Long id, String name, BigDecimal price, ProductStatus status, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.status = status;
        this.stock = stock;
    }

    public void deductStock(int quantity) {
        if(this.stock < quantity) {
            throw new CustomException(ErrorCode.SOLD_OUT_PRODUCT);
        }
        this.stock -= quantity;
    }

}
