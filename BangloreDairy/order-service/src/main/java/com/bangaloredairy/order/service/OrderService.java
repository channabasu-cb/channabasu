package com.bangaloredairy.order.service;

import com.bangaloredairy.common.dto.CartItemDTO;
import com.bangaloredairy.common.dto.OrderRequestDTO;
import com.bangaloredairy.common.events.OrderCreatedEvent;
import com.bangaloredairy.order.model.Order;
import com.bangaloredairy.order.model.OrderItem;
import com.bangaloredairy.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventProducer orderEventProducer;

    @Transactional
    public Order placeOrder(OrderRequestDTO request) {
        String orderNumber = "BLR-DRY-" + LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) + "-"
                + UUID.randomUUID().toString().substring(0, 6).toUpperCase();

        BigDecimal subtotal = BigDecimal.ZERO;
        for (CartItemDTO item : request.getItems()) {
            BigDecimal itemTotal = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            item.setTotalPrice(itemTotal);
            subtotal = subtotal.add(itemTotal);
        }

        BigDecimal deliveryFee = request.getDeliveryFee() != null ? request.getDeliveryFee()
                : (subtotal.compareTo(new BigDecimal("199.00")) >= 0 ? BigDecimal.ZERO : new BigDecimal("25.00"));
        BigDecimal totalAmount = subtotal.add(deliveryFee);

        LocalDate deliveryDate = request.getDeliveryDate() != null ? request.getDeliveryDate()
                : LocalDate.now().plusDays(1); // Default next morning delivery

        Order order = Order.builder()
                .orderNumber(orderNumber)
                .userId(request.getUserId() != null ? request.getUserId() : 1L)
                .customerName(request.getCustomerName() != null ? request.getCustomerName() : "Customer")
                .customerEmail(request.getCustomerEmail() != null ? request.getCustomerEmail() : "channa@bangaloredairy.in")
                .customerPhone(request.getCustomerPhone() != null ? request.getCustomerPhone() : "+91 98450 12345")
                .orderType(request.getOrderType() != null ? request.getOrderType() : "ON_DEMAND")
                .orderStatus("CONFIRMED")
                .deliverySlot(request.getDeliverySlot() != null ? request.getDeliverySlot() : "MORNING_5_30_AM")
                .deliveryDate(deliveryDate)
                .deliveryAddress(request.getDeliveryAddress() != null ? request.getDeliveryAddress() : "#128, 4th Cross, Indiranagar, Bangalore")
                .pincode(request.getPincode() != null ? request.getPincode() : "560038")
                .subtotal(subtotal)
                .deliveryFee(deliveryFee)
                .tax(BigDecimal.ZERO)
                .totalAmount(totalAmount)
                .paymentMode(request.getPaymentMode() != null ? request.getPaymentMode() : "WALLET")
                .paymentStatus("PAID")
                .build();

        for (CartItemDTO item : request.getItems()) {
            OrderItem orderItem = OrderItem.builder()
                    .productId(item.getProductId())
                    .productName(item.getProductName())
                    .unitPrice(item.getUnitPrice())
                    .quantity(item.getQuantity())
                    .totalPrice(item.getTotalPrice())
                    .build();
            order.addItem(orderItem);
        }

        Order savedOrder = orderRepository.save(order);
        log.info("Order created successfully: ID={}, Number={}, Total=₹{}", savedOrder.getId(), savedOrder.getOrderNumber(), savedOrder.getTotalAmount());

        // Publish OrderCreatedEvent to Kafka for Async Email Notification & Inventory Processing
        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .orderId(savedOrder.getId())
                .orderNumber(savedOrder.getOrderNumber())
                .userId(savedOrder.getUserId())
                .customerName(savedOrder.getCustomerName())
                .customerEmail(savedOrder.getCustomerEmail())
                .customerPhone(savedOrder.getCustomerPhone())
                .orderType(savedOrder.getOrderType())
                .orderStatus(savedOrder.getOrderStatus())
                .deliverySlot(savedOrder.getDeliverySlot())
                .deliveryDate(savedOrder.getDeliveryDate())
                .deliveryAddress(savedOrder.getDeliveryAddress())
                .pincode(savedOrder.getPincode())
                .subtotal(savedOrder.getSubtotal())
                .deliveryFee(savedOrder.getDeliveryFee())
                .totalAmount(savedOrder.getTotalAmount())
                .paymentMode(savedOrder.getPaymentMode())
                .paymentStatus(savedOrder.getPaymentStatus())
                .items(request.getItems())
                .build();

        orderEventProducer.publishOrderCreatedEvent(event);

        return savedOrder;
    }

    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public Order getOrderByNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with number: " + orderNumber));
    }
}
