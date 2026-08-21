package com.interviewcraft.auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fullName;

    private String role; // "ROLE_USER", "ROLE_ADMIN"

    private String targetRole; // e.g. "Senior Backend Engineer (Java/Spring Boot)"

    private Integer yearsOfExperience;

    private String targetCompanyTier; // e.g. "FAANG", "Tier-1 Product", "Early Stage Startup"

    private String preparationTimeline; // e.g. "30 Days", "60 Days", "90 Days"

    private String primaryTechStack; // e.g. "Java 21, Spring Boot, PostgreSQL, Kafka, AWS, Docker"

    private String currentFocus;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
