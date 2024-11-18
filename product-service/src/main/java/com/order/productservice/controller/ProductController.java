package com.order.productservice.controller;

import com.order.productservice.facade.RedissonLockProductFacade;
import com.order.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;


    // 전체 상품 조회
//    @GetMapping()
//    public ResponseEntity<List<ProductSummaryResponseDto>> getAllProducts(){
//        List<ProductSummaryResponseDto> products = productService.getAllProducts();
//        return ResponseEntity.status(HttpStatus.OK).body(products);
//    }

    @GetMapping("/tests")
    public void test(){
        productService.decrease(2L,1L);
    }
}
