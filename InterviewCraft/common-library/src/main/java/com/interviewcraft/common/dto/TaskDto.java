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
public class TaskDto {
    private Long id;
    private Long milestoneId;
    private Integer dayNumber;
    private String title;
    private String description;
    private String category; // "CODING_PRACTICE", "SYSTEM_DESIGN", "READING", "VIDEO_WATCH", "MOCK_INTERVIEW"
    private Integer estimatedMinutes;
    private boolean completed;
    private String resourceUrl;
    private LocalDateTime completedAt;
}
