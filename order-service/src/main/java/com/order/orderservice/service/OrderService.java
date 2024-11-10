package com.order.orderservice.service;


import com.order.orderservice.dto.OrderRequestDto;
import com.order.orderservice.dto.OrderResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface OrderService {


    OrderResponseDto createOrder (long userId, OrderRequestDto orderRequestDto);

    List<OrderResponseDto> getOrdersByUserId(long userId);

}