package com.order.userservice.dto;

import lombok.Getter;

import java.util.Date;

// order-service 프로젝트 데이터 값
@Getter
public class OrderClientResponseDto {
    private long productId;
    private long orderId;

    private String orderStatus;
    // 주문 시점
    private Date createdAt;
}
