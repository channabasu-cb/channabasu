package com.bangaloredairy.notification.controller;

import com.bangaloredairy.common.dto.ApiResponse;
import com.bangaloredairy.common.events.EmailNotificationEvent;
import com.bangaloredairy.common.events.OrderCreatedEvent;
import com.bangaloredairy.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class NotificationController {

    private final EmailService emailService;

    @GetMapping("/recent")
    public ResponseEntity<ApiResponse<List<EmailNotificationEvent>>> getRecentNotifications() {
        List<EmailNotificationEvent> logs = emailService.getSentEmailLogs();
        return ResponseEntity.ok(ApiResponse.ok(logs, "Found " + logs.size() + " recent notifications"));
    }

    @PostMapping("/send-test")
    public ResponseEntity<ApiResponse<String>> sendTestNotification(@RequestBody OrderCreatedEvent testEvent) {
        emailService.processOrderCreatedNotification(testEvent);
        return ResponseEntity.ok(ApiResponse.ok("Test email notification triggered", "Success"));
    }
}
