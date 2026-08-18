package com.bangaloredairy.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionRequestDTO {
    private Long userId;
    private Long productId;
    private Integer quantity;
    private String frequency; // DAILY, ALTERNATE_DAYS, WEEKDAYS_ONLY, WEEKENDS_ONLY
    private String deliverySlot; // MORNING_5_30_AM, EVENING_5_30_PM
    private LocalDate startDate;
    private LocalDate endDate;
    private String deliveryAddress;
    private String pincode;
    private String specialInstructions;
}
