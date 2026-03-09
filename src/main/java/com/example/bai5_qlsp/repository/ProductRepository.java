package com.example.bai5_qlsp.repository;

import com.example.bai5_qlsp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
}
