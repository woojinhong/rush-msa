package com.order.orderservice.entity;

import com.order.orderservice.common.entity.TimeStamp;
import com.order.orderservice.dto.OrderRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Orders extends TimeStamp implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long productId;

    // 주문 상태
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Builder
    public Orders(Long userId, Long productId) {
        this.userId = userId;
        this.productId = productId;
    }

    public static Orders toEntity(OrderRequestDto orderRequestDto, long userId){
        return Orders.builder()
                .userId(userId)
                .productId(orderRequestDto.getProductId())
                .build();
    }
}