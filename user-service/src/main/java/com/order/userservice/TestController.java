package com.order.userservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ms1")


public class TestController {
    @GetMapping("/test")
    public void test(){
        System.out.println("1");
    }
}
