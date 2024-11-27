package com.order.productservice.service;

import com.order.productservice.common.aop.DistributedLock;
import com.order.productservice.dto.ProductSummaryResponseDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductService {
    @Transactional
    void restockInRedis(Long productId, Long quantity);

    List<ProductSummaryResponseDto> getAllProducts();

    void decrease(Long productId,Long quantity);


    void decreaseSynchronized(Long productId, Long quantity);

    @Transactional
    void decreaseWithPessimisticLock(Long productId, Long quantity);

    @Transactional
    void decreaseWithRedis(Long productId);
}
