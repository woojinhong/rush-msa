package com.order.productservice.service;

import com.order.productservice.entity.Products;
import com.order.productservice.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest

class ProductServiceImplTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

//    @Autowired
//    private PessimisticLockProductServiceImpl pessimisticLockProductService;


    @BeforeEach
    void setUp() {
        System.out.println("Running @BeforeEach setup method");
        productRepository.saveAndFlush(new Products("jordan nike 1", "조던 컬레버레이션", BigDecimal.valueOf(1000.00), 100L));
    }

//    @AfterEach
//    void tearDown() {
//        productRepository.deleteAll();
//    }

    @Test
    @DisplayName("decrease product stock")
    void decrease() {
        productService.decrease(1L,1L);

        Products product = productRepository.findById(1L).orElseThrow();

        assertEquals(99,product.getProductStock());
    }

    @Test
    @DisplayName("decrease 1000 thread stock")
    void threadDecrease() throws InterruptedException {
        int threadCount = 1000;

        ExecutorService ex = Executors.newFixedThreadPool(32);

        CountDownLatch latch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++){
            ex.submit(()->{
                try {
                    productService.decreaseWithPessimisticLock(1L, 1L);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        Products product = productRepository.findById(1L).orElseThrow(()->
                new IllegalArgumentException("product not found 에러"));

        assertEquals(9000,product.getProductStock());
    }
}