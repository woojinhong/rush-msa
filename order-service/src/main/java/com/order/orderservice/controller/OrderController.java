package com.order.orderservice.controller;

import com.order.orderservice.dto.OrderRequestDto;
import com.order.orderservice.dto.OrderResponseDto;
import com.order.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/{userId}")
    public ResponseEntity<OrderResponseDto> createOrder(@PathVariable("userId") long userId,
                                                     @RequestBody OrderRequestDto orderRequestDto) {
        OrderResponseDto createOrder = orderService.createOrder(userId, orderRequestDto);
        return ResponseEntity.ok().body(createOrder);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<List<OrderResponseDto>> getOrdersByUserId(@PathVariable long userId) {
        List<OrderResponseDto> getOrdersByUser =  orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok().body(getOrdersByUser);
    }
}
