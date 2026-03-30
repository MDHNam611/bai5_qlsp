package com.example.bai5_qlsp.controller;

import com.example.bai5_qlsp.service.CartService;
import com.example.bai5_qlsp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/checkout")
    public String showCheckoutForm(Model model) {
        if (cartService.getCartItems().isEmpty()) {
            return "redirect:/cart";
        }
        model.addAttribute("cartItems", cartService.getCartItems());
        model.addAttribute("totalPrice", cartService.getTotalPrice());
        return "cart/checkout";
    }

    @PostMapping("/checkout")
    public String processCheckout(@RequestParam("customerName") String customerName,
                                  @RequestParam("phone") String phone,
                                  @RequestParam("address") String address) {
        orderService.createOrder(customerName, phone, address);
        return "redirect:/order-success";
    }

    @GetMapping("/order-success")
    public String orderSuccess() {
        return "cart/order-success";    
    }
}