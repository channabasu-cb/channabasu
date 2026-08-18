package com.bangaloredairy.order.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "subscriptions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private String productName;

    @Builder.Default
    @Column(nullable = false)
    private Integer quantity = 1;

    @Column(nullable = false, length = 30)
    private String frequency; // DAILY, ALTERNATE_DAYS, WEEKDAYS_ONLY, WEEKENDS_ONLY

    @Column(nullable = false, length = 30)
    private String deliverySlot; // MORNING_5_30_AM, EVENING_5_30_PM

    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate endDate;

    @Builder.Default
    @Column(length = 30)
    private String status = "ACTIVE"; // ACTIVE, PAUSED, CANCELLED

    @Column(nullable = false, columnDefinition = "TEXT")
    private String deliveryAddress;

    @Column(length = 10)
    private String pincode;

    private String specialInstructions;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
