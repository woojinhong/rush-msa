package com.order.userservice.client;

import com.order.userservice.dto.OrderClientResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "order-service")
public interface OrderServiceClient {

    @GetMapping("/orders/{userId}")
    List<OrderClientResponseDto> getOrdersByUserId(@PathVariable("userId") long userId);
}