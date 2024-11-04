package com.order.userservice.dto;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
public class OrderRequestDto {
    private long productId;
    private long orderId;
    // 상품 주문 시점 수량
    private BigDecimal productOrderedQuantity;
    // 상품 주문 시점 가격
    private BigDecimal productOrderedPrice;

    // 주문 시점
    private Date createdAt;
}
