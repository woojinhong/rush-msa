package com.order.productservice.service;

import com.order.productservice.dto.ProductSummaryResponseDto;
import com.order.productservice.entity.Products;
import com.order.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;


    // 상품 생성
//    public void createOrUpdateProduct(ProductRequestDto requestDto) {
//        String name = requestDto.getName();
//        long stockQuantity = requestDto.getStock();
//
//        // 비관적 락을 이용해 상품 조회
//        Products products = productRepository.findByProductName(name)
//                .orElseGet(() -> ProductMapper.toEntity(requestDto));
//
//        // 재고 업데이트 또는 새 상품의 초기 재고 설정
//        products.increaseStock(stockQuantity);
//
//        // 상품 저장 (신규 생성 또는 업데이트)
//        productRepository.save(products);
//    }

    // 상품 목록 조회
    @Override
    public List<ProductSummaryResponseDto> getAllProducts() {
        List<Products> products = productRepository.findAll();
        return products.stream()
                .map(ProductSummaryResponseDto::toSummaryDto)
                .collect(Collectors.toList());
    }

//    // 상품 상세 조회
//    public ProductDetailResponseDto getProduct(Long productId) {
//        Products products = getProductById(productId);
//        return ProductMapper.toDetailDto(products);
//    }
//
//
//    public Products getProductById(Long productId) {
//        return productRepository.findById(productId)
//                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
//    }
//

}