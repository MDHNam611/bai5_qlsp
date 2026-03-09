package com.example.bai5_qlsp.config;

import com.example.bai5_qlsp.model.Category;
import com.example.bai5_qlsp.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        // Kiểm tra xem bảng Category đã có dữ liệu chưa
        if (categoryRepository.count() == 0) {
            Category cat1 = new Category();
            cat1.setName("Điện thoại");
            
            Category cat2 = new Category();
            cat2.setName("Laptop");
            
            Category cat3 = new Category();
            cat3.setName("Phụ kiện");

            // Lưu vào database
            categoryRepository.save(cat1);
            categoryRepository.save(cat2);
            categoryRepository.save(cat3);
            
            System.out.println("Đã khởi tạo dữ liệu mẫu cho Category thành công!");
        }
    }
}