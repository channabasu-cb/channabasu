package com.interviewcraft.plan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "plan_tasks")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "milestone_id", nullable = false)
    @JsonIgnore
    private PlanMilestone milestone;

    private Integer dayNumber;

    @Column(nullable = false)
    private String title;

    @Column(length = 2048)
    private String description;

    private String category; // "CODING_PRACTICE", "SYSTEM_DESIGN", "READING", "VIDEO_WATCH", "MOCK_INTERVIEW"

    private Integer estimatedMinutes;

    @Builder.Default
    private boolean completed = false;

    @Column(length = 1024)
    private String resourceUrl;

    private LocalDateTime completedAt;
}
