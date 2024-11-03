package com.order.userservice.controller;

import com.order.userservice.dto.UserSignUpRequestDto;
import com.order.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // user sign up 성공
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserSignUpRequestDto signUpDto) {
        userService.signUp(signUpDto);
        return ResponseEntity.ok("회원가입 성공");
    }
}