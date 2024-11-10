package com.order.productservice.controller;

import com.order.productservice.dto.ProductSummaryResponseDto;
import com.order.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product-service/product")
public class ProductController {

    private final ProductService productService;

    // 전체 상품 조회
    @GetMapping()
    public ResponseEntity<List<ProductSummaryResponseDto>> getAllProducts(){
        List<ProductSummaryResponseDto> products = productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }
}
