package com.order.productservice.repository;

import com.order.productservice.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Products,Long> {


    Optional<Products> findById(Long productId);
}