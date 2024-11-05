package com.order.productservice.repository;

import com.order.productservice.entity.Products;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Products,Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Products> findByProductName(String name);

}