package com.interviewcraft.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {
    private Long id;
    private String sessionId;
    private Long userId;
    private String sender; // "USER" or "AI"
    private String message;
    private String intent; // "GREETING", "SKILL_PROBING", "TECH_ASSESSMENT", "PLAN_READY", "GENERAL"
    private LocalDateTime timestamp;
}
