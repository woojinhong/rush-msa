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
public class ProductSummaryResponseDto {
    private Long productId;
    private String productName;
    private BigDecimal productPrice;

    public static ProductSummaryResponseDto toSummaryDto(Products product) {
        return ProductSummaryResponseDto.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .build();
    }
}