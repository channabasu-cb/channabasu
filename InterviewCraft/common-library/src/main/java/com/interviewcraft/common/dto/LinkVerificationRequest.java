package com.interviewcraft.common.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LinkVerificationRequest {
    @NotBlank(message = "URL to verify is required")
    private String url;
    private String resourceTitle;
    private String expectedCategory;
}
