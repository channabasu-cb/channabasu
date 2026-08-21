package com.interviewcraft.plan.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "interview_plans")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    private String sessionId;

    @Column(nullable = false)
    private String title;

    private String targetRole;

    private String targetCompanyTier;

    private Integer totalWeeks;

    private Integer totalEstimatedHours;

    @Builder.Default
    private Integer completedHours = 0;

    @Builder.Default
    private Integer readinessScore = 0; // 0 - 100%

    @Builder.Default
    private String status = "IN_PROGRESS"; // "IN_PROGRESS", "COMPLETED", "PAUSED"

    private LocalDate startDate;

    private LocalDate targetCompletionDate;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("sequenceOrder ASC")
    @Builder.Default
    private List<PlanMilestone> milestones = new ArrayList<>();

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
