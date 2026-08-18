package com.bangaloredairy.notification.service;

import com.bangaloredairy.common.events.EmailNotificationEvent;
import com.bangaloredairy.common.events.OrderCreatedEvent;
import jakarta.mail.internet.MimeMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final EmailTemplateBuilder templateBuilder;

    @Value("${app.mail.from-email:orders@bangaloredairy.in}")
    private String fromEmail;

    @Value("${app.mail.from-name:Bangalore Dairy}")
    private String fromName;

    // In-memory cache of dispatched emails for inspection via REST / Web UI
    @Getter
    private final List<EmailNotificationEvent> sentEmailLogs = Collections.synchronizedList(new ArrayList<>());

    public void processOrderCreatedNotification(OrderCreatedEvent event) {
        log.info("Processing async email notification for OrderNumber: {}", event.getOrderNumber());

        String subject = "🥛 Order Confirmed! Bangalore Dairy #" + event.getOrderNumber();
        String htmlContent = templateBuilder.buildOrderConfirmationHtml(event);
        String recipientEmail = event.getCustomerEmail() != null ? event.getCustomerEmail() : "channa@bangaloredairy.in";

        EmailNotificationEvent logEvent = EmailNotificationEvent.builder()
                .recipientEmail(recipientEmail)
                .recipientName(event.getCustomerName())
                .subject(subject)
                .templateType("ORDER_CONFIRMATION")
                .htmlContent(htmlContent)
                .timestamp(LocalDateTime.now())
                .build();

        sentEmailLogs.add(0, logEvent);
        if (sentEmailLogs.size() > 50) {
            sentEmailLogs.remove(sentEmailLogs.size() - 1);
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromEmail, fromName);
            helper.setTo(recipientEmail);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            log.info("Dispatched HTML email confirmation to {}", recipientEmail);
        } catch (Exception e) {
            log.warn("Could not dispatch via SMTP server (local mock active): {}", e.getMessage());
        }
    }
}
