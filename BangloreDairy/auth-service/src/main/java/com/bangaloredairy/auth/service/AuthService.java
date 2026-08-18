package com.bangaloredairy.auth.service;

import com.bangaloredairy.auth.model.User;
import com.bangaloredairy.auth.repository.UserRepository;
import com.bangaloredairy.auth.security.JwtTokenProvider;
import com.bangaloredairy.common.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public UserDTO login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        String token = jwtTokenProvider.generateToken(user.getId(), user.getEmail(), user.getRole());
        return mapToDTO(user, token);
    }

    @Transactional
    public UserDTO register(UserDTO request, String rawPassword) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email is already registered. Please login.");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(rawPassword))
                .phone(request.getPhone())
                .address(request.getAddress())
                .area(request.getArea() != null ? request.getArea() : "Indiranagar, Bangalore")
                .pincode(request.getPincode() != null ? request.getPincode() : "560038")
                .walletBalance(new BigDecimal("500.00")) // Welcome credit bonus ₹500
                .role("ROLE_CUSTOMER")
                .build();

        User saved = userRepository.save(user);
        String token = jwtTokenProvider.generateToken(saved.getId(), saved.getEmail(), saved.getRole());
        return mapToDTO(saved, token);
    }

    public UserDTO getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return mapToDTO(user, null);
    }

    @Transactional
    public UserDTO updateWallet(Long userId, BigDecimal amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setWalletBalance(user.getWalletBalance().add(amount));
        User saved = userRepository.save(user);
        return mapToDTO(saved, null);
    }

    private UserDTO mapToDTO(User user, String token) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .area(user.getArea())
                .pincode(user.getPincode())
                .walletBalance(user.getWalletBalance())
                .role(user.getRole())
                .token(token)
                .build();
    }
}
