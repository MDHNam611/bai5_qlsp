package com.example.bai5_qlsp.controller;

import com.example.bai5_qlsp.model.Product;
import com.example.bai5_qlsp.service.CategoryService;
import com.example.bai5_qlsp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String listProducts(Model model) {
        List<Product> productList = productService.getAllProducts();
        model.addAttribute("products", productList);
        return "product/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product/add";
    }

    // CẬP NHẬT: Thêm @RequestParam để nhận file ảnh
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product,
                              @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        
        // Kiểm tra xem người dùng có chọn file ảnh không
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                // Lấy tên file gốc
                String filename = imageFile.getOriginalFilename();
                
                // Định nghĩa đường dẫn lưu file vào thư mục static/images
                Path path = Paths.get("src/main/resources/static/images/" + filename);
                
                // Tạo thư mục nếu chưa tồn tại
                Files.createDirectories(path.getParent());
                
                // Ghi file vào ổ cứng
                Files.write(path, imageFile.getBytes());
                
                // Lưu đường dẫn ảnh vào Database
                product.setImage("/images/" + filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        productService.saveProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id);
        if (product != null) {
            model.addAttribute("product", product);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "product/add";
        }
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}