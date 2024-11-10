package com.order.orderservice.repository;

import com.order.orderservice.entity.Notifications;
import com.order.orderservice.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notifications, Long> {



}
