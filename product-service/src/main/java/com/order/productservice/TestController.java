package com.order.productservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/product")
public class TestController {
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("hi");
    }

    @GetMapping()
    public ResponseEntity<String> tests() {
        return ResponseEntity.ok("his");
    }
}
