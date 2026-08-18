package com.bangaloredairy.common.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailNotificationEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    private String recipientEmail;
    private String recipientName;
    private String subject;
    private String templateType; // ORDER_CONFIRMATION, DAILY_DELIVERY_ALERT, SUBSCRIPTION_UPDATE
    private String plainTextContent;
    private String htmlContent;
    private Map<String, Object> templateModel;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}
