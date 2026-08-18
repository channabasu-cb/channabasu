package com.bangaloredairy.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {
    private Long userId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String orderType; // ON_DEMAND, DAILY_SUBSCRIPTION_DISPATCH
    private String deliverySlot; // MORNING_5_30_AM, EVENING_5_30_PM
    private LocalDate deliveryDate;
    private String deliveryAddress;
    private String pincode;
    private String paymentMode; // WALLET, UPI, COD, NETBANKING
    private BigDecimal deliveryFee;
    private List<CartItemDTO> items;
    private String specialInstructions;
}
