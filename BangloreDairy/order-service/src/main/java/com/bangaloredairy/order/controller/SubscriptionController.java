package com.bangaloredairy.order.controller;

import com.bangaloredairy.common.dto.ApiResponse;
import com.bangaloredairy.common.dto.SubscriptionRequestDTO;
import com.bangaloredairy.order.model.Subscription;
import com.bangaloredairy.order.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<ApiResponse<Subscription>> createSubscription(@RequestBody SubscriptionRequestDTO request) {
        try {
            Subscription sub = subscriptionService.createSubscription(request);
            return ResponseEntity.ok(ApiResponse.ok(sub, "Daily dairy subscription activated successfully!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Failed to create subscription: " + e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Subscription>>> getUserSubscriptions(@PathVariable Long userId) {
        List<Subscription> subs = subscriptionService.getUserSubscriptions(userId);
        return ResponseEntity.ok(ApiResponse.ok(subs, "Found " + subs.size() + " subscriptions"));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Subscription>> updateStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            Subscription sub = subscriptionService.updateStatus(id, status);
            return ResponseEntity.ok(ApiResponse.ok(sub, "Subscription status updated to " + status));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
