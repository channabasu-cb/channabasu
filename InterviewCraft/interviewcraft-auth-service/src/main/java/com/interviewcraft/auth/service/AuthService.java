package com.interviewcraft.auth.service;

import com.interviewcraft.auth.model.User;
import com.interviewcraft.auth.repository.UserRepository;
import com.interviewcraft.common.dto.AuthResponse;
import com.interviewcraft.common.dto.LoginRequest;
import com.interviewcraft.common.dto.RegisterRequest;
import com.interviewcraft.common.dto.UserDto;
import com.interviewcraft.common.exception.AppException;
import com.interviewcraft.common.exception.ResourceNotFoundException;
import com.interviewcraft.common.exception.UnauthorizedException;
import com.interviewcraft.common.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException("Email is already registered: " + request.getEmail());
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .role("ROLE_USER")
                .targetRole(request.getTargetRole() != null ? request.getTargetRole() : "Senior Software Engineer")
                .yearsOfExperience(request.getYearsOfExperience() != null ? request.getYearsOfExperience() : 3)
                .targetCompanyTier(request.getTargetCompanyTier() != null ? request.getTargetCompanyTier() : "Tier-1 Product")
                .preparationTimeline(request.getPreparationTimeline() != null ? request.getPreparationTimeline() : "30 Days")
                .primaryTechStack(request.getPrimaryTechStack() != null ? request.getPrimaryTechStack() : "Java, Spring Boot, Microservices")
                .build();

        User savedUser = userRepository.save(user);
        log.info("Registered new user with ID: {} and email: {}", savedUser.getId(), savedUser.getEmail());

        String token = jwtUtils.generateToken(savedUser.getId(), savedUser.getEmail(), savedUser.getFullName(), savedUser.getRole());
        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .expiresIn(jwtUtils.getExpiration())
                .user(mapToDto(savedUser))
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid email or password");
        }

        log.info("User successfully logged in: {}", user.getEmail());
        String token = jwtUtils.generateToken(user.getId(), user.getEmail(), user.getFullName(), user.getRole());
        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .expiresIn(jwtUtils.getExpiration())
                .user(mapToDto(user))
                .build();
    }

    public UserDto getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return mapToDto(user);
    }

    @Transactional
    public UserDto updateProfile(Long userId, UserDto updateDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        if (updateDto.getFullName() != null) user.setFullName(updateDto.getFullName());
        if (updateDto.getTargetRole() != null) user.setTargetRole(updateDto.getTargetRole());
        if (updateDto.getYearsOfExperience() != null) user.setYearsOfExperience(updateDto.getYearsOfExperience());
        if (updateDto.getTargetCompanyTier() != null) user.setTargetCompanyTier(updateDto.getTargetCompanyTier());
        if (updateDto.getPreparationTimeline() != null) user.setPreparationTimeline(updateDto.getPreparationTimeline());
        if (updateDto.getPrimaryTechStack() != null) user.setPrimaryTechStack(updateDto.getPrimaryTechStack());
        if (updateDto.getCurrentFocus() != null) user.setCurrentFocus(updateDto.getCurrentFocus());

        User updated = userRepository.save(user);
        return mapToDto(updated);
    }

    public UserDto mapToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .targetRole(user.getTargetRole())
                .yearsOfExperience(user.getYearsOfExperience())
                .targetCompanyTier(user.getTargetCompanyTier())
                .preparationTimeline(user.getPreparationTimeline())
                .primaryTechStack(user.getPrimaryTechStack())
                .currentFocus(user.getCurrentFocus())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
