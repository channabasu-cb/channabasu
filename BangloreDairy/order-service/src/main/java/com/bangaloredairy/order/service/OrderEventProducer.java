package com.bangaloredairy.order.service;

import com.bangaloredairy.common.events.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${app.kafka.topics.order-created:dairy.orders.created}")
    private String orderCreatedTopic;

    public void publishOrderCreatedEvent(OrderCreatedEvent event) {
        log.info("Publishing OrderCreatedEvent to Kafka topic '{}': OrderNumber={}", orderCreatedTopic, event.getOrderNumber());
        try {
            kafkaTemplate.send(orderCreatedTopic, event.getOrderNumber(), event)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            log.info("Successfully published order event for OrderNumber: {} at offset: {}",
                                    event.getOrderNumber(), result.getRecordMetadata().offset());
                        } else {
                            log.warn("Failed to publish order event to Kafka topic (running in graceful standalone/fallback mode): {}", ex.getMessage());
                        }
                    });
        } catch (Exception e) {
            log.warn("Kafka broker unreachable, event logged locally: {}", e.getMessage());
        }
    }
}
