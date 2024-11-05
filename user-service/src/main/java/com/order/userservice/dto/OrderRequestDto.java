package com.order.userservice.dto;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

// order-service 프로젝트 데이터 값
@Getter
public class OrderRequestDto {
    private long productId;
    private long orderId;
    // 상품 주문 시점 수량
    private BigDecimal OrderedQuantity;
    // 상품 주문 시점 가격
    private BigDecimal OrderedPrice;

    // 주문 시점
    private Date createdAt;
}
