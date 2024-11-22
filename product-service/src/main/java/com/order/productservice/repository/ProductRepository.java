package com.order.productservice.repository;

import com.order.productservice.entity.Products;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Products,Long> {


    // 비관적 락(Pessimistic Lock) 적용
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Products p WHERE p.productId = :productId")
    Optional<Products> findByPessimisticLockId(@Param("productId") Long productId);

    // 단순 조회: 재고를 조회할 때 사용
    @Query("SELECT p.productStock FROM Products p WHERE p.productId = :productId")
    @Lock(LockModeType.NONE)
    Optional<Long> findStockByProductId(@Param("productId") Long productId);

    Optional<Products> findByProductId(Long productId);
}