package com.order.productservice.common.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
@RequiredArgsConstructor
public class DistributedLockAspect {

    private final RedissonClient redissonClient;
    private final TransactionalLockService transactionalLockService;

    @Around("@annotation(distributedLock)")
    public Object lockAndExecute(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) throws Throwable {
        String key = distributedLock.key();
        RLock lock = redissonClient.getLock(key);
        boolean available = lock.tryLock(distributedLock.waitTime(), distributedLock.leaseTime(), distributedLock.timeUnit());

        if (!available) {
            throw new IllegalStateException("Lock acquisition failed");
        }

        try {
            // 트랜잭션을 Propagation.REQUIRES_NEW로 실행하여 커밋 후 락을 해제하도록 AOP로 제어
            return transactionalLockService.proceedWithNewTransaction(joinPoint);
        } finally {
            lock.unlock();
        }
    }
}

