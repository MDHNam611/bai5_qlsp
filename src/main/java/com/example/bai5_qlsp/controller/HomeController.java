package com.example.bai5_qlsp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/home")
    @ResponseBody
    public String home(Principal principal) {
        return "Hello, " + principal.getName();
    }

    @GetMapping("/403")
    public String accessDenied() {
        return "403"; 
    }

    // THÊM ĐOẠN NÀY ĐỂ MỞ TRANG ĐĂNG NHẬP
    @GetMapping("/login")
    public String login() {
        return "login"; // Trả về file login.html trong thư mục templates
    }
}