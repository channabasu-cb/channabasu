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
public class UserDto {
    private Long id;
    private String email;
    private String fullName;
    private String targetRole;
    private Integer yearsOfExperience;
    private String targetCompanyTier;
    private String preparationTimeline;
    private String primaryTechStack;
    private String currentFocus;
    private LocalDateTime createdAt;
}
