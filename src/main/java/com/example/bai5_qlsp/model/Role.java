package com.example.bai5_qlsp.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column
    private String name; // Tên role phải có tiền tố "ROLE_", ví dụ: ROLE_ADMIN, ROLE_USER
}