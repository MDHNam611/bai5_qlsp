package com.example.bai5_qlsp.repository;

import com.example.bai5_qlsp.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword% AND (:categoryId IS NULL OR p.category.id = :categoryId)")
    Page<Product> searchProducts(@Param("keyword") String keyword, @Param("categoryId") Long categoryId, Pageable pageable);
}