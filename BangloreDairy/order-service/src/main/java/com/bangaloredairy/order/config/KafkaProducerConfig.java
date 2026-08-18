package com.bangaloredairy.order.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaProducerConfig {

    @Value("${app.kafka.topics.order-created:dairy.orders.created}")
    private String orderCreatedTopic;

    @Value("${app.kafka.topics.order-status:dairy.orders.status}")
    private String orderStatusTopic;

    @Bean
    public NewTopic orderCreatedTopic() {
        return TopicBuilder.name(orderCreatedTopic)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic orderStatusTopic() {
        return TopicBuilder.name(orderStatusTopic)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
