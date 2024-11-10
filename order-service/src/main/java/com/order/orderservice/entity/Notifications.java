package com.order.orderservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Notifications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;
    @Column(nullable = false)
    private boolean notificationSent;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private Long productId;
}
