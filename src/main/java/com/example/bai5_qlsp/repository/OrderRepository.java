package com.example.bai5_qlsp.repository;

import com.example.bai5_qlsp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}