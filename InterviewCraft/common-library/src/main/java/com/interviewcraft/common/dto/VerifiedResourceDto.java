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
public class VerifiedResourceDto {
    private Long id;
    private String title;
    private String authorOrChannel;
    private String category; // "BOOK", "ONLINE_TUTORIAL", "YOUTUBE_CHANNEL", "DOCUMENTATION", "PRACTICE_PLATFORM"
    private String url;
    private String domain;
    private String description;
    private String difficultyLevel; // "ALL_LEVELS", "BEGINNER", "INTERMEDIATE", "ADVANCED"
    private List<String> topics;
    private boolean isVerifiedWorking;
    private Integer httpStatusCode;
    private Long responseTimeMs;
    private String verificationBadge; // "VERIFIED_ACTIVE", "VALIDATED_OFFICIAL", "COMMUNITY_GOLD_STANDARD"
    private Double rating;
    private LocalDateTime lastVerifiedAt;
}
