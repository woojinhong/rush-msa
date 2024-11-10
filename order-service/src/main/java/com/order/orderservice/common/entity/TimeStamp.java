package com.order.orderservice.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class TimeStamp {

    @CreatedDate
    @Column(updatable = false)
    protected LocalDateTime createdAt;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
