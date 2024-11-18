package com.order.userservice.controller;
import com.order.userservice.dto.UserOrderResponseDto;
import com.order.userservice.dto.UserSignUpRequestDto;
import com.order.userservice.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;
    private final Environment env;
    // user sign up 성공
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserSignUpRequestDto signUpDto) {
        userService.signUp(signUpDto);
        return ResponseEntity.ok("회원가입 성공");
    }
    @GetMapping("/{userId}")
    public UserOrderResponseDto getUserByUserId(@PathVariable("userId") long userId) {
        return userService.getUserByUserId(userId);
    }
    @GetMapping("/health-check")
    public String status() {
        return env.getProperty("local.server.port")
                + ", token= "+env.getProperty("token.secret")
                + ", token exp= "+env.getProperty("token.expiration_time")
                ;
    }
}