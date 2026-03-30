package com.example.bai5_qlsp.service;

import com.example.bai5_qlsp.model.Product;
import com.example.bai5_qlsp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


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

    // Tìm kiếm và phân trang sản phẩm
    public Page<Product> findPaginated(int pageNo, int pageSize, String sortDir, String keyword, Long categoryId) {
        // Cấu hình sắp xếp theo giá
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by("price").ascending() : Sort.by("price").descending();
        
        // PageRequest trong Spring Data bắt đầu từ số 0
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        
        if (keyword == null) {
            keyword = "";
        }
        return productRepository.searchProducts(keyword, categoryId, pageable);
    }
}