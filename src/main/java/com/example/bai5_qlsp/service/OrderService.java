package com.example.bai5_qlsp.service;

import com.example.bai5_qlsp.model.CartItem;
import com.example.bai5_qlsp.model.Order;
import com.example.bai5_qlsp.model.OrderDetail;
import com.example.bai5_qlsp.model.Product;
import com.example.bai5_qlsp.repository.OrderDetailRepository;
import com.example.bai5_qlsp.repository.OrderRepository;
import com.example.bai5_qlsp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartService cartService;

    @Transactional
    public void createOrder(String customerName, String phone, String address) {
        List<CartItem> cartItems = cartService.getCartItems();
        if (cartItems.isEmpty()) {
            return;
        }

        // 1. Tạo Order
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setPhone(phone);
        order.setAddress(address);
        order.setOrderDate(new Date());
        order.setTotalPrice(cartService.getTotalPrice());
        order = orderRepository.save(order); // Lưu Order để lấy ID

        // 2. Lưu Order Detail
        for (CartItem item : cartItems) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setQuantity(item.getQuantity());
            
            // Lấy lại entity Product từ DB
            Product product = productRepository.findById(item.getProductId()).orElse(null);
            detail.setProduct(product);
            
            orderDetailRepository.save(detail);
        }

        // 3. Xóa giỏ hàng sau khi đặt thành công
        cartService.clearCart();
    }
}