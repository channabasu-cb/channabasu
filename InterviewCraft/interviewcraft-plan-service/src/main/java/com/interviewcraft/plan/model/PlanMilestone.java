package com.interviewcraft.plan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "plan_milestones")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanMilestone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    @JsonIgnore
    private InterviewPlan plan;

    private Integer sequenceOrder;

    @Column(nullable = false)
    private String phaseName;

    @Column(length = 2048)
    private String description;

    private Integer weekNumber;

    private Integer estimatedHours;

    @Builder.Default
    private boolean completed = false;

    @OneToMany(mappedBy = "milestone", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("dayNumber ASC")
    @Builder.Default
    private List<PlanTask> tasks = new ArrayList<>();
}
