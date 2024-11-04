package com.order.userservice.service;

import com.order.userservice.dto.UserOrderResponseDto;
import com.order.userservice.dto.UserSignUpRequestDto;

public interface UserService {
    void signUp(UserSignUpRequestDto userSignUpDto);

    UserOrderResponseDto getUserByUserId(long userId);
}
