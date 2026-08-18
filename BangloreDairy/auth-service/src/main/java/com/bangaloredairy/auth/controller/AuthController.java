package com.bangaloredairy.auth.controller;

import com.bangaloredairy.auth.service.AuthService;
import com.bangaloredairy.common.dto.ApiResponse;
import com.bangaloredairy.common.dto.UserDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    @Data
    public static class LoginRequest {
        private String email;
        private String password;
    }

    @Data
    public static class RegisterRequest {
        private String name;
        private String email;
        private String password;
        private String phone;
        private String address;
        private String area;
        private String pincode;
    }

    @Data
    public static class WalletTopupRequest {
        private Long userId;
        private BigDecimal amount;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserDTO>> login(@RequestBody LoginRequest req) {
        try {
            UserDTO user = authService.login(req.getEmail(), req.getPassword());
            return ResponseEntity.ok(ApiResponse.ok(user, "Login successful. Welcome back!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDTO>> register(@RequestBody RegisterRequest req) {
        try {
            UserDTO dto = UserDTO.builder()
                    .name(req.getName())
                    .email(req.getEmail())
                    .phone(req.getPhone())
                    .address(req.getAddress())
                    .area(req.getArea())
                    .pincode(req.getPincode())
                    .build();
            UserDTO user = authService.register(dto, req.getPassword());
            return ResponseEntity.ok(ApiResponse.ok(user, "Registration successful! ₹500 welcome credit added to your dairy wallet."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<ApiResponse<UserDTO>> getProfile(@PathVariable Long userId) {
        try {
            UserDTO user = authService.getUserProfile(userId);
            return ResponseEntity.ok(ApiResponse.ok(user, "User profile retrieved"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/wallet/topup")
    public ResponseEntity<ApiResponse<UserDTO>> topupWallet(@RequestBody WalletTopupRequest req) {
        try {
            UserDTO user = authService.updateWallet(req.getUserId(), req.getAmount());
            return ResponseEntity.ok(ApiResponse.ok(user, "Wallet recharged successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
