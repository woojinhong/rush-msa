package com.order.userservice.dto;


import lombok.Getter;

@Getter
public class UserSignUpRequestDto {
    private String email;
    private String password;
    private String userName;
    private String contact;
    private String address;
}
