package com.bangaloredairy.notification.consumer;

import com.bangaloredairy.common.events.OrderCreatedEvent;
import com.bangaloredairy.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventConsumer {

    private final EmailService emailService;

    @KafkaListener(topics = "${app.kafka.topics.order-created:dairy.orders.created}", groupId = "${spring.kafka.consumer.group-id:notification-service-group}")
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("Received Kafka OrderCreatedEvent for order: {}", event.getOrderNumber());
        try {
            emailService.processOrderCreatedNotification(event);
        } catch (Exception e) {
            log.error("Error processing OrderCreatedEvent for order {}: {}", event.getOrderNumber(), e.getMessage(), e);
        }
    }
}
