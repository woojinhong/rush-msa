package com.order.orderservice.repository;

import com.order.orderservice.entity.Orders;


import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {


    Orders findByOrderId(long orderId);

    List<Orders> findByUserId(long userId);

}
