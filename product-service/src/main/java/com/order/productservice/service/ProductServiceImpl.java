package com.order.productservice.service;

import com.order.productservice.dto.ProductSummaryResponseDto;
import com.order.productservice.entity.Products;
import com.order.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final RedisTemplate<String, String> readOnlyRedisTemplate;

    // 생성자 작성
    public ProductServiceImpl(
            ProductRepository productRepository,
            @Qualifier("redisTemplate") RedisTemplate<String, String> redisTemplate,
            @Qualifier("readOnlyRedisTemplate") RedisTemplate<String, String> readOnlyRedisTemplate
    ) {
        this.productRepository = productRepository;
        this.redisTemplate = redisTemplate;
        this.readOnlyRedisTemplate = readOnlyRedisTemplate;
    }

    private static final String STOCK_KEY_PREFIX = "product:stock:";



    /**
     * 재입고 시 Redis에 재고 추가
     * @param productId 상품 ID
     * @param quantity 추가할 재고 수량
     */
    @Transactional
    @Override
    public void restockInRedis(Long productId, Long quantity) {
        String redisKey = STOCK_KEY_PREFIX + productId;

        // Redis에서 재고 값을 원자적으로 증가
        redisTemplate.opsForValue().increment(redisKey, quantity);

        System.out.println("재입고 완료: Product ID " + productId + "에 " + quantity + "개 추가됨.");
    }

    @Transactional
    public void restock(Long productId, Long quantity) {
        // 1. DB 재고 업데이트
        productRepository.updateStock(productId, quantity);

        // 2. Redis 동기화
        String redisKey = STOCK_KEY_PREFIX + productId;
        Long updatedStock = productRepository.findStockByProductId(productId)
                .orElseThrow(() -> new IllegalStateException("Product not found"));

        redisTemplate.opsForValue().set(redisKey, updatedStock.toString());
    }

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


    @Override
//    @DistributedLock(key = "productLock", waitTime = 15L, leaseTime = 3L)
    public void decrease(Long productId, Long quantity) {
        Products product = getProducts(productRepository.findById(productId));
        product.decreaseStock(quantity);

        productRepository.saveAndFlush(product);
    }

    private final Map<Long, Object> locks = new ConcurrentHashMap<>();

    @Override
    public void decreaseSynchronized(Long productId, Long quantity) {
        // 고유 락 객체 생성 또는 가져오기
        Object lock = locks.computeIfAbsent(productId, k -> new Object());
        System.out.println("Lock for productId " + productId + ": " + System.identityHashCode(lock));

        synchronized (lock) {
            // 상품 조회
            Products product = getProducts(productRepository.findById(productId));

            // 재고 감소
                product.decreaseStock(quantity);

            // RDB 저장
            productRepository.saveAndFlush(product);
        }

        // 필요 없는 락 객체 정리 (Optional)
        locks.remove(productId);
    }


    private Products getProducts(Optional<Products> productRepository) {
        Products product = productRepository.orElseThrow(()
                -> new IllegalArgumentException("product not found"));
        return product;
    }

    @Transactional
    @Override
    public void decreaseWithPessimisticLock(Long productId, Long quantity) {
        Products product = getProducts(productRepository.findByPessimisticLockId(productId));
        product.decreaseStock(quantity);

        productRepository.saveAndFlush(product);
    }




    @Transactional
    @Override
    public void decreaseWithRedis(Long productId) {
        // Redis 키 생성
        String redisKey = STOCK_KEY_PREFIX + productId;

        // 1. Redis에서 재고 확인
        String redisStockString = readOnlyRedisTemplate.opsForValue().get(redisKey);
        Long remainingStock;

        if (redisStockString == null) {
            // 2. 캐시 미스: DB에서 재고 조회
            remainingStock = getStockFromDB(productId);

            // 3. DB 재고 유효성 검증
            if (remainingStock == null || remainingStock <= 0) {
                throw new IllegalStateException("Insufficient stock in database");
            }

            // 4. Redis에 초기화
            redisTemplate.opsForValue().set(redisKey, remainingStock.toString());
        } else {
            // Redis에서 가져온 값을 Long으로 변환
            try {
                remainingStock = Long.parseLong(redisStockString);
            } catch (NumberFormatException e) {
                throw new IllegalStateException("Invalid stock data in Redis for key: " + redisKey, e);
            }
        }
        // 5. Redis에서 원자적으로 재고 감소
        //Long decrementedStock = redisTemplate.opsForValue().decrement(redisKey, quantity);
        // 5. Redis에서 원자적으로 재고 1 감소
        Long decrementedStock = redisTemplate.opsForValue().decrement(redisKey);

        // 6. 재고 부족 시 복구 및 예외 처리
        if (decrementedStock != null && decrementedStock < 0) {
            redisTemplate.opsForValue().increment(redisKey); // 복구
            throw new IllegalStateException("Insufficient stock");
        }
    }

    // DB에서 재고 조회
    public Long getStockFromDB(Long productId) {
        return productRepository.findStockByProductId(productId)
                .orElseThrow(() -> new IllegalStateException("Product not found or no stock available"));
    }


    // 주기적으로 Redis 데이터를 DB에 반영하는 메서드 (예시)
//    @Scheduled(fixedRate = 60000) // 60초마다 실행
//    public void syncStockToDatabase() {
//        // 모든 제품의 ID 목록을 가져와서 DB 업데이트 (또는 별도의 Redis Key 스캔)
//        List<Products> products = productRepository.findAll();
//        for (Products product : products) {
//            String redisKey = STOCK_KEY_PREFIX + product.getProductId();
//            Long stock = redisTemplate.opsForValue().get(redisKey);
//
//            // Redis에 저장된 재고를 DB에 반영
//            if (stock != null) {
//                product.setStock(stock);
//                productRepository.save(product);
//            }
//        }
//    }
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

