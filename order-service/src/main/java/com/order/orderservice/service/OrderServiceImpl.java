package com.order.orderservice.service;

import com.order.orderservice.dto.OrderRequestDto;
import com.order.orderservice.dto.OrderResponseDto;
import com.order.orderservice.entity.OrderStatus;
import com.order.orderservice.entity.Orders;
import com.order.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;



    @Override
    public OrderResponseDto createOrder(long userId, OrderRequestDto orderRequestDto) {

        // dto -> entity
        Orders order = Orders.toEntity(orderRequestDto, userId);

        // 주문 대기 상태
        order.setOrderStatus(OrderStatus.PENDING);

        // 주문 저장
        orderRepository.save(order);

        // 주문 반환 값
        OrderResponseDto orderResponseDto = OrderResponseDto.toOrderDto(order);

        return orderResponseDto;
    }


    // 유저의 주문 목록 조회
    @Override
    public List<OrderResponseDto> getOrdersByUserId(long userId) {

        List<Orders> getOrders = orderRepository.findByUserId(userId);

        return getOrders.stream()
                .map(OrderResponseDto::toOrderDto)
                .collect(Collectors.toList());
    }
}
