package com.interviewcraft.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewPlanDto {
    private Long id;
    private Long userId;
    private String sessionId;
    private String title;
    private String targetRole;
    private String targetCompanyTier;
    private Integer totalWeeks;
    private Integer totalEstimatedHours;
    private Integer completedHours;
    private Integer readinessScore; // 0 - 100%
    private String status; // "IN_PROGRESS", "COMPLETED", "PAUSED"
    private LocalDate startDate;
    private LocalDate targetCompletionDate;
    private List<MilestoneDto> milestones;
    private List<VerifiedResourceDto> curatedResources;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
