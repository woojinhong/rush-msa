package com.order.userservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")


public class TestController {
    @GetMapping("/test")
    public void test(){
        System.out.println("1");
    }
}
