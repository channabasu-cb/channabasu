package com.bangaloredairy.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long categoryId;
    private String categoryName;
    private String name;
    private String brand;
    private String description;
    private String unitSize;
    private BigDecimal price;
    private BigDecimal discountedPrice;
    private Integer stockQuantity;
    private Boolean isAvailable;
    private Boolean supportsDailySubscription;
    private String fatContent;
    private String snfContent;
    private Integer shelfLifeDays;
    private String imageUrl;
}
