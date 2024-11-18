package com.order.productservice.service;

import com.order.productservice.dto.ProductSummaryResponseDto;

import java.util.List;

public interface ProductService {
    List<ProductSummaryResponseDto> getAllProducts();

    void decrease(Long productId,Long quantity);
}
