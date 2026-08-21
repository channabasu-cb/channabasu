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
public class LinkVerificationResult {
    private String url;
    private String normalizedUrl;
    private boolean isValidAndWorking;
    private Integer httpStatusCode;
    private String statusMessage;
    private Long responseTimeMs;
    private String contentType;
    private String pageTitle;
    private String serverHeader;
    private boolean sslValid;
    private String domainTrustScore; // "HIGH", "MEDIUM", "SUSPICIOUS", "UNREACHABLE"
    private LocalDateTime checkedAt;
}
