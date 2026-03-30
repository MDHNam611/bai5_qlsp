package com.example.bai5_qlsp.service;

import com.example.bai5_qlsp.model.CartItem;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@SessionScope
public class CartService {
    private List<CartItem> cartItems = new ArrayList<>();

    // Thêm sản phẩm vào giỏ hàng (Câu 5)
    public void addToCart(CartItem item) {
        Optional<CartItem> existingItem = cartItems.stream()
                .filter(c -> c.getProductId().equals(item.getProductId()))
                .findFirst();

        if (existingItem.isPresent()) {
            // Nếu sản phẩm đã có trong giỏ, tăng số lượng
            existingItem.get().setQuantity(existingItem.get().getQuantity() + item.getQuantity());
        } else {
            // Nếu chưa có, thêm mới vào giỏ
            cartItems.add(item);
        }
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void clearCart() {
        cartItems.clear();
    }

    // Tính tổng tiền toàn bộ giỏ hàng
    public long getTotalPrice() {
        return cartItems.stream().mapToLong(CartItem::getTotal).sum();
    }

    // Cập nhật số lượng sản phẩm trong giỏ
    public void updateQuantity(Long productId, int quantity) {
        Optional<CartItem> existingItem = cartItems.stream()
                .filter(c -> c.getProductId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            if (quantity <= 0) {
                cartItems.remove(existingItem.get()); // Nếu số lượng <= 0 thì xóa luôn
            } else {
                existingItem.get().setQuantity(quantity);
            }
        }
    }

    // Xóa một sản phẩm khỏi giỏ
    public void removeItem(Long productId) {
        cartItems.removeIf(c -> c.getProductId().equals(productId));
    }
}