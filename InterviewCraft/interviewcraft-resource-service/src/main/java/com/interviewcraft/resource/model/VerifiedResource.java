package com.interviewcraft.resource.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "verified_resources")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerifiedResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String authorOrChannel;

    @Column(nullable = false)
    private String category; // "BOOK", "ONLINE_TUTORIAL", "YOUTUBE_CHANNEL", "DOCUMENTATION", "PRACTICE_PLATFORM"

    @Column(nullable = false, length = 1024, unique = true)
    private String url;

    private String domain;

    @Column(length = 2048)
    private String description;

    private String difficultyLevel; // "ALL_LEVELS", "BEGINNER", "INTERMEDIATE", "ADVANCED"

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "resource_topics", joinColumns = @JoinColumn(name = "resource_id"))
    @Column(name = "topic")
    @Builder.Default
    private List<String> topics = new ArrayList<>();

    @Builder.Default
    private boolean isVerifiedWorking = true;

    private Integer httpStatusCode;

    private Long responseTimeMs;

    private String verificationBadge; // "VERIFIED_ACTIVE", "VALIDATED_OFFICIAL", "COMMUNITY_GOLD_STANDARD"

    private Double rating;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime lastVerifiedAt;
}
