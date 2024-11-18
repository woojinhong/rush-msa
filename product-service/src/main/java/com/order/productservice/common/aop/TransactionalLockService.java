package com.order.productservice.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionalLockService {

    //(propagation = Propagation.REQUIRES_NEW)
    @Transactional
    public Object proceedWithNewTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }
}
