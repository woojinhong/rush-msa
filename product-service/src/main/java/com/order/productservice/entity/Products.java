package com.order.productservice.entity;

import com.order.productservice.common.entity.TimeStamp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Getter
public class Products extends TimeStamp implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @Column(nullable = false)
    private String productName;

    private String productDesc;

    // 상품 판매가
    @Column(nullable = false)
    private BigDecimal productPrice;

//    @Column(nullable = false)
    private long productStock = 0;

    @Builder
    public Products(String productName, String productDesc, BigDecimal productPrice, long productStock) {
        this.productName = productName;
        this.productDesc = productDesc;
        this.productPrice = productPrice;
        this.productStock = productStock;
    }

    // 상품 수량 증가 메서드 (비즈니스 로직)
    public void increaseStock(long stockQuantity) {
        this.productStock += stockQuantity;
    }

    // 상품 수량 감소 메서드 (비즈니스 로직)
//    public void decreaseStock(long amount) {
//        if (this.stock < amount) {
//            throw new IllegalArgumentException("재고가 부족합니다.");
//        }
//        this.stock -= amount;
//    }
}


