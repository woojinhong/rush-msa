package com.order.productservice.dto;

import com.order.productservice.entity.Products;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailResponseDto {
    private Long productId;
    private String productName;
    private BigDecimal productPrice;
    private String productDesc;
    private long productStock;

    public static ProductDetailResponseDto toDetailDto(Products product){
        return ProductDetailResponseDto.builder()
                .productId(product.getProductId())
                .productDesc(product.getProductName())
                .productPrice(product.getProductPrice())
                .productDesc(product.getProductDesc())
                .productStock(product.getProductStock())
                .build();
    }

}