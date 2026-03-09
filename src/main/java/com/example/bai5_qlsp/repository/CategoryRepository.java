package com.example.bai5_qlsp.repository;

import com.example.bai5_qlsp.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    
}
