package com.bangaloredairy.common.events;

import com.bangaloredairy.common.dto.CartItemDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long orderId;
    private String orderNumber;
    private Long userId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String orderType;
    private String orderStatus;
    private String deliverySlot;
    private LocalDate deliveryDate;
    private String deliveryAddress;
    private String pincode;
    private BigDecimal subtotal;
    private BigDecimal deliveryFee;
    private BigDecimal totalAmount;
    private String paymentMode;
    private String paymentStatus;
    private List<CartItemDTO> items;
    @Builder.Default
    private LocalDateTime eventTimestamp = LocalDateTime.now();
}
