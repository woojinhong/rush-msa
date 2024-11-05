package com.order.productservice.service;

import com.order.productservice.dto.ProductSummaryResponseDto;
import com.order.productservice.entity.Products;

import java.util.List;

public interface ProductService {
    List<ProductSummaryResponseDto> getAllProducts();
}
