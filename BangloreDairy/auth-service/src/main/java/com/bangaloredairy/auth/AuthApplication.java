package com.bangaloredairy.auth;

import com.bangaloredairy.auth.model.User;
import com.bangaloredairy.auth.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@SpringBootApplication
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

    @Bean
    CommandLineRunner initUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) {
                User demoUser = User.builder()
                        .name("Channabasappa Ullagaddi")
                        .email("channa@bangaloredairy.in")
                        .password(passwordEncoder.encode("password123"))
                        .phone("+91 98450 12345")
                        .address("#128, 4th Cross, CMH Road, Indiranagar")
                        .area("Indiranagar")
                        .pincode("560038")
                        .walletBalance(new BigDecimal("1250.00"))
                        .role("ROLE_CUSTOMER")
                        .build();
                userRepository.save(demoUser);

                User adminUser = User.builder()
                        .name("Dairy Operations Admin")
                        .email("admin@bangaloredairy.in")
                        .password(passwordEncoder.encode("admin123"))
                        .phone("+91 80 2222 8888")
                        .address("Bengaluru Dairy Circle, Hosur Road")
                        .area("Dairy Circle")
                        .pincode("560029")
                        .walletBalance(new BigDecimal("5000.00"))
                        .role("ROLE_ADMIN")
                        .build();
                userRepository.save(adminUser);
            }
        };
    }
}
