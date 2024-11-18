package com.order.productservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/product")
public class TestController {
    @GetMapping("/test")
    public String test() {
        return "hi";
    }

    @GetMapping("/check")
    public String tests() {
        return "check check";
    }
}
