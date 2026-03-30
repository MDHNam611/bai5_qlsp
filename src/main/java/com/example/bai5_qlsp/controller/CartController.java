package com.example.bai5_qlsp.controller;

import com.example.bai5_qlsp.model.CartItem;
import com.example.bai5_qlsp.model.Product;
import com.example.bai5_qlsp.service.CartService;
import com.example.bai5_qlsp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    // Chỉ nhận productId, mặc định số lượng thêm là 1
    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") Long productId) {
        Product product = productService.getProductById(productId);
        if (product != null) {
            CartItem item = new CartItem(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    1, // Mặc định thêm 1 sản phẩm
                    product.getImage()
            );
            cartService.addToCart(item);
        }
        return "redirect:/products"; // Thêm xong ở lại trang danh sách thay vì nhảy sang giỏ hàng
    }

    // Xử lý cập nhật số lượng từ trang giỏ hàng
    @PostMapping("/update")
    public String updateQuantity(@RequestParam("productId") Long productId,
                                 @RequestParam("quantity") int quantity) {
        cartService.updateQuantity(productId, quantity);
        return "redirect:/cart";
    }

    // Xóa sản phẩm khỏi giỏ
    @GetMapping("/remove/{id}")
    public String removeFromCart(@PathVariable("id") Long productId) {
        cartService.removeItem(productId);
        return "redirect:/cart";
    }

    @GetMapping
    public String viewCart(Model model) {
        model.addAttribute("cartItems", cartService.getCartItems());
        model.addAttribute("totalPrice", cartService.getTotalPrice());
        return "cart/cart";
    }
}