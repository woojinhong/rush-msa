package com.order.productservice.common.aop;

import org.springframework.transaction.annotation.Propagation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributedLock {

    /**
     * 락 키 (필수)
     */
    String key();

    /**
     * 락을 획득하기 위해 기다릴 최대 시간
     * 기본값: 5초
     */
    long waitTime() default 15L;

    /**
     * 락 임대 시간 (자동 해제 시간)
     * 기본값: 3초
     */
    long leaseTime() default 1L;

    /**
     * 시간 단위
     * 기본값: SECONDS
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 트랜잭션 전파 레벨 설정
     * 기본값: Propagation.REQUIRES_NEW
     */
    Propagation propagation() default Propagation.REQUIRES_NEW;
}