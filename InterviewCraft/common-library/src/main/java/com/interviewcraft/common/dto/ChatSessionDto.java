package com.interviewcraft.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatSessionDto {
    private String sessionId;
    private Long userId;
    private String title;
    private String status; // "ACTIVE", "ASSESSMENT_COMPLETED", "ARCHIVED"
    private SkillProfileDto extractedProfile;
    private List<ChatMessageDto> messages;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
