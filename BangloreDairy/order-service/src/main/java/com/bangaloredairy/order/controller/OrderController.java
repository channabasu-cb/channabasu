package com.bangaloredairy.order.controller;

import com.bangaloredairy.common.dto.ApiResponse;
import com.bangaloredairy.common.dto.OrderRequestDTO;
import com.bangaloredairy.order.model.Order;
import com.bangaloredairy.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<Order>> placeOrder(@RequestBody OrderRequestDTO request) {
        try {
            Order order = orderService.placeOrder(request);
            return ResponseEntity.ok(ApiResponse.ok(order, "Order placed successfully! Order Number: " + order.getOrderNumber()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Failed to place order: " + e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Order>>> getUserOrders(@PathVariable Long userId) {
        List<Order> orders = orderService.getUserOrders(userId);
        return ResponseEntity.ok(ApiResponse.ok(orders, "Found " + orders.size() + " orders"));
    }

    @GetMapping("/{orderNumber}")
    public ResponseEntity<ApiResponse<Order>> getOrderByNumber(@PathVariable String orderNumber) {
        try {
            Order order = orderService.getOrderByNumber(orderNumber);
            return ResponseEntity.ok(ApiResponse.ok(order, "Order details retrieved"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
