//package com.order.productservice.facade;
//
//
//import com.order.productservice.service.ProductService;
//import lombok.RequiredArgsConstructor;
//import org.redisson.api.RLock;
//import org.redisson.api.RedissonClient;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.TimeUnit;
//
//
//@Component
//@RequiredArgsConstructor
//public class RedissonLockProductFacade {
//    private final RedissonClient redissonClient;
//    private final ProductService productService;
//
//
//
//    public void decrease(Long productId, Long quantity) {
//        RLock lock = redissonClient.getLock(productId.toString());
//
//
//        try {
//            boolean available = lock.tryLock(20, 1, TimeUnit.SECONDS);
//
//            if (!available) {
//                System.out.println("lock fail");
//                return;
//            }
//
//            productService.decrease(productId, quantity);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        } finally {
//            lock.unlock();
//        }
//    }
//}
