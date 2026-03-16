package com.example.bai5_qlsp.config;

import com.example.bai5_qlsp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AccountService accountService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) 
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/products/add", "/products/edit", "/products/delete").hasRole("ADMIN")
                        .requestMatchers("/order").hasRole("USER")
                        .requestMatchers("/products").hasAnyRole("USER", "ADMIN")
                        // THÊM: Cho phép truy cập tự do vào /login và /error
                        .requestMatchers("/error", "/login").permitAll() 
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login") // CHỈ ĐỊNH RÕ TRANG ĐĂNG NHẬP CỦA MÌNH
                        .defaultSuccessUrl("/products", true) 
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") 
                        .logoutSuccessUrl("/login?logout") // Giờ nó sẽ mở login.html và hiện thông báo màu xanh
                        .invalidateHttpSession(true)       
                        .clearAuthentication(true)         
                        .deleteCookies("JSESSIONID")       
                        .permitAll()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/403")
                );

        return http.build();
    }
}