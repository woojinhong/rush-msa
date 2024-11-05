package com.order.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {
    private String name;
    private BigDecimal price;
    private String description;
    private long stock;

}
