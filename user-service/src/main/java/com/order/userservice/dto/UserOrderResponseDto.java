package com.order.userservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.order.userservice.entity.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserOrderResponseDto {

    private Long userId;
    private String email;
    private String name;

    private List<OrderRequestDto> orders;


    @Builder
    public UserOrderResponseDto(Long userId, String email, String name, List<OrderRequestDto> orders) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.orders = orders;
    }

    public static UserOrderResponseDto toDto(Users user){
        return UserOrderResponseDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .orders(new ArrayList<>())
                .build();
    }

}
