package com.order.productservice.service;

import com.order.productservice.dto.ProductSummaryResponseDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductService {
    List<ProductSummaryResponseDto> getAllProducts();

    void decrease(Long productId,Long quantity);

    @Transactional
    void decreaseWithPessimisticLock(Long productId, Long quantity);

    @Transactional
    void decreaseWithRedis(Long productId, Long quantity);
}
