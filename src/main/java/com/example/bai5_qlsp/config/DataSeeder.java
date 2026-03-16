package com.example.bai5_qlsp.config;

import com.example.bai5_qlsp.model.Account;
import com.example.bai5_qlsp.model.Category;
import com.example.bai5_qlsp.model.Role;
import com.example.bai5_qlsp.repository.AccountRepository;
import com.example.bai5_qlsp.repository.CategoryRepository;
import com.example.bai5_qlsp.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        
        // 1. Kiểm tra và khởi tạo dữ liệu mẫu cho Category
        if (categoryRepository.count() == 0) {
            Category cat1 = new Category();
            cat1.setName("Điện thoại");
            
            Category cat2 = new Category();
            cat2.setName("Laptop");
            
            Category cat3 = new Category();
            cat3.setName("Phụ kiện");

            categoryRepository.save(cat1);
            categoryRepository.save(cat2);
            categoryRepository.save(cat3);
            
            System.out.println("Đã khởi tạo dữ liệu mẫu cho Category thành công!");
        }

        // 2. Kiểm tra và khởi tạo dữ liệu mẫu cho Role
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseGet(() -> {
            Role role = new Role();
            role.setName("ROLE_ADMIN");
            return roleRepository.save(role);
        });

        Role userRole = roleRepository.findByName("ROLE_USER").orElseGet(() -> {
            Role role = new Role();
            role.setName("ROLE_USER");
            return roleRepository.save(role);
        });

        // 3. Kiểm tra và tạo tài khoản "admin" với mật khẩu "admin123"
        if (accountRepository.findByLoginName("admin").isEmpty()) {
            Account admin = new Account();
            admin.setLogin_name("admin");
            admin.setPassword(passwordEncoder.encode("admin123")); 
            
            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            admin.setRoles(roles);
            
            accountRepository.save(admin);
            System.out.println("Đã tạo thành công tài khoản: admin / admin123");
        }

        // 4. Kiểm tra và tạo tài khoản "user" với mật khẩu "123456"
        if (accountRepository.findByLoginName("user").isEmpty()) {
            Account userAccount = new Account();
            userAccount.setLogin_name("user");
            userAccount.setPassword(passwordEncoder.encode("123456")); 
            
            Set<Role> roles = new HashSet<>();
            roles.add(userRole);
            userAccount.setRoles(roles);
            
            accountRepository.save(userAccount);
            System.out.println("Đã tạo thành công tài khoản: user / 123456");
        }
    }
}