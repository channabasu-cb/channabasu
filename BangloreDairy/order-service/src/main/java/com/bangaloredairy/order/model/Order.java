package com.bangaloredairy.order.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 64)
    private String orderNumber;

    @Column(nullable = false)
    private Long userId;

    private String customerName;
    private String customerEmail;
    private String customerPhone;

    @Builder.Default
    @Column(length = 30)
    private String orderType = "ON_DEMAND"; // ON_DEMAND, DAILY_SUBSCRIPTION_DISPATCH

    @Builder.Default
    @Column(length = 30)
    private String orderStatus = "CONFIRMED"; // PENDING, CONFIRMED, PROCESSING, OUT_FOR_DELIVERY, DELIVERED, CANCELLED

    @Column(nullable = false, length = 30)
    private String deliverySlot; // MORNING_5_30_AM, EVENING_5_30_PM

    @Column(nullable = false)
    private LocalDate deliveryDate;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String deliveryAddress;

    @Column(nullable = false, length = 10)
    private String pincode;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal deliveryFee = BigDecimal.ZERO;

    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal tax = BigDecimal.ZERO;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Builder.Default
    @Column(length = 30)
    private String paymentMode = "WALLET"; // WALLET, UPI, COD, NETBANKING

    @Builder.Default
    @Column(length = 30)
    private String paymentStatus = "PAID"; // PAID, PENDING, FAILED

    @Builder.Default
    private Boolean emailNotificationSent = false;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }
}
