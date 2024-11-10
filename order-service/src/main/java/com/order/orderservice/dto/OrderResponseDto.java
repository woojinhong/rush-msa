package com.order.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.order.orderservice.entity.OrderStatus;
import com.order.orderservice.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OrderResponseDto {
    private Long orderId;

    private Long userId;
    private Long productId;

    private OrderStatus orderStatus;

    private LocalDateTime createdAt;

    public static OrderResponseDto toOrderDto(Orders order){
        return OrderResponseDto.builder()
                .orderId(order.getOrderId())
                .userId(order.getUserId())
                .productId(order.getProductId())
                .orderStatus(order.getOrderStatus())
                .createdAt(order.getCreatedAt())
                .build();
    }
}