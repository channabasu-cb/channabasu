package com.interviewcraft.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkillProfileDto {
    private String targetRole;
    private Integer yearsOfExperience;
    private String targetCompanyTier;
    private String preparationTimeline;
    private List<String> primaryTechStack;
    private List<String> identifiedStrengths;
    private List<String> criticalGaps;
    private Map<String, Integer> skillProficiencyMap; // e.g. "DSA": 65, "System Design": 40, "Spring Boot": 80
    private String readinessLevel; // "BEGINNER", "INTERMEDIATE", "ADVANCED"
    private String summaryRecommendation;
}
