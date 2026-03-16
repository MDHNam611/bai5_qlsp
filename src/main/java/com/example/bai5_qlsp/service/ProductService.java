package com.example.bai5_qlsp.service;

import com.example.bai5_qlsp.model.Product;
import com.example.bai5_qlsp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Lấy toàn bộ sản phẩm từ Database
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Lấy sản phẩm theo ID từ Database
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    // Lưu (hoặc Cập nhật) sản phẩm vào Database
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    // Xóa sản phẩm khỏi Database
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}