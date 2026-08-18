package com.bangaloredairy.catalog.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 100)
    private String brand;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 50)
    private String unitSize;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(precision = 10, scale = 2)
    private BigDecimal discountedPrice;

    @Builder.Default
    private Integer stockQuantity = 100;

    @Builder.Default
    private Boolean isAvailable = true;

    @Builder.Default
    private Boolean supportsDailySubscription = true;

    @Column(length = 50)
    private String fatContent;

    @Column(length = 50)
    private String snfContent;

    @Builder.Default
    private Integer shelfLifeDays = 2;

    private String imageUrl;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
