package com.interviewcraft.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MilestoneDto {
    private Long id;
    private Long planId;
    private Integer sequenceOrder;
    private String phaseName; // e.g. "Phase 1: DSA & Core Problem Solving"
    private String description;
    private Integer weekNumber;
    private Integer estimatedHours;
    private boolean completed;
    private List<TaskDto> tasks;
    private List<VerifiedResourceDto> milestoneResources;
}
