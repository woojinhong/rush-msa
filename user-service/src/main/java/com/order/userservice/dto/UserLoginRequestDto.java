package com.order.userservice.dto;

import lombok.Getter;

@Getter
public class UserLoginRequestDto {

    private String email;
    private String pwd;
}
