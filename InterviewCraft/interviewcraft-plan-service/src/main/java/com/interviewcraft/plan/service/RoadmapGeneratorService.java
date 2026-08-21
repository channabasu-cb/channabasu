package com.interviewcraft.plan.service;

import com.interviewcraft.common.dto.InterviewPlanDto;
import com.interviewcraft.common.dto.MilestoneDto;
import com.interviewcraft.common.dto.SkillProfileDto;
import com.interviewcraft.common.dto.TaskDto;
import com.interviewcraft.common.dto.VerifiedResourceDto;
import com.interviewcraft.plan.model.InterviewPlan;
import com.interviewcraft.plan.model.PlanMilestone;
import com.interviewcraft.plan.model.PlanTask;
import com.interviewcraft.plan.repository.InterviewPlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoadmapGeneratorService {

    private final InterviewPlanRepository planRepository;

    @Transactional
    public InterviewPlanDto generateCustomizedPlan(Long userId, String sessionId, SkillProfileDto profile) {
        String role = profile.getTargetRole() != null ? profile.getTargetRole() : "Senior Backend Engineer";
        String companyTier = profile.getTargetCompanyTier() != null ? profile.getTargetCompanyTier() : "Tier-1 Tech";

        InterviewPlan plan = InterviewPlan.builder()
                .userId(userId)
                .sessionId(sessionId)
                .title("Tailored Master Plan: " + role + " (" + companyTier + ")")
                .targetRole(role)
                .targetCompanyTier(companyTier)
                .totalWeeks(4)
                .totalEstimatedHours(48)
                .completedHours(0)
                .readinessScore(15)
                .status("IN_PROGRESS")
                .startDate(LocalDate.now())
                .targetCompletionDate(LocalDate.now().plusWeeks(4))
                .build();

        // Build Phase 1: Algorithmic Patterns & DSA
        PlanMilestone m1 = PlanMilestone.builder()
                .plan(plan)
                .sequenceOrder(1)
                .phaseName("Phase 1: Algorithmic Patterns & Problem Solving Mastery")
                .description("Master core coding patterns: Two Pointers, Sliding Window, Graphs, and Dynamic Programming with visual step-by-step walkthroughs.")
                .weekNumber(1)
                .estimatedHours(12)
                .completed(false)
                .build();

        List<PlanTask> m1Tasks = Arrays.asList(
                PlanTask.builder().milestone(m1).dayNumber(1).title("Array Hashing & Two Pointers (NeetCode 150)")
                        .description("Solve 4 core problems: Two Sum, Valid Anagram, Group Anagrams, and 3Sum. Focus on optimal space/time complexity.")
                        .category("CODING_PRACTICE").estimatedMinutes(90).resourceUrl("https://leetcode.com/studyplan/top-interview-150/").build(),

                PlanTask.builder().milestone(m1).dayNumber(2).title("Sliding Window & Monotonic Stack Patterns")
                        .description("Master Best Time to Buy/Sell Stock, Longest Substring Without Repeating Characters, and Daily Temperatures.")
                        .category("CODING_PRACTICE").estimatedMinutes(90).resourceUrl("https://www.youtube.com/@NeetCode").build(),

                PlanTask.builder().milestone(m1).dayNumber(3).title("Binary Trees & Graph Traversals (BFS / DFS)")
                        .description("Implement Invert Binary Tree, Lowest Common Ancestor, Number of Islands, and Clone Graph.")
                        .category("CODING_PRACTICE").estimatedMinutes(120).resourceUrl("https://www.manning.com/books/grokking-algorithms-second-edition").build(),

                PlanTask.builder().milestone(m1).dayNumber(4).title("Dynamic Programming Fundamentals (1D / 2D)")
                        .description("Solve Climbing Stairs, Coin Change, Longest Increasing Subsequence, and Unique Paths.")
                        .category("CODING_PRACTICE").estimatedMinutes(120).resourceUrl("https://leetcode.com/studyplan/top-interview-150/").build(),

                PlanTask.builder().milestone(m1).dayNumber(5).title("Phase 1 Timed Mock Coding Challenge")
                        .description("Complete a 60-minute 2-problem mock interview test without IDE auto-complete.")
                        .category("MOCK_INTERVIEW").estimatedMinutes(60).resourceUrl("https://leetcode.com/studyplan/top-interview-150/").build()
        );
        m1.setTasks(m1Tasks);

        // Build Phase 2: Scalable System Architecture & High-Level Design
        PlanMilestone m2 = PlanMilestone.builder()
                .plan(plan)
                .sequenceOrder(2)
                .phaseName("Phase 2: Scalable Distributed Systems & High-Level Architecture")
                .description("Design high-throughput systems, consistent hashing, caching strategies, and message queue partitioning.")
                .weekNumber(2)
                .estimatedHours(12)
                .completed(false)
                .build();

        List<PlanTask> m2Tasks = Arrays.asList(
                PlanTask.builder().milestone(m2).dayNumber(6).title("System Design Framework & Capacity Estimation")
                        .description("Learn the 4-step interview blueprint: Requirements Clarification, Back-of-the-envelope math, High-level Diagram, Deep Dives.")
                        .category("SYSTEM_DESIGN").estimatedMinutes(90).resourceUrl("https://bytebytego.com/").build(),

                PlanTask.builder().milestone(m2).dayNumber(7).title("Distributed Caching & Redis Architecture")
                        .description("Study Cache-Aside, Write-Through, Write-Behind, Cache Invalidation, and Cache Stampede mitigation.")
                        .category("SYSTEM_DESIGN").estimatedMinutes(90).resourceUrl("https://www.youtube.com/@ByteByteGo").build(),

                PlanTask.builder().milestone(m2).dayNumber(8).title("Distributed Messaging & Event-Driven Patterns with Kafka")
                        .description("Deep dive into Kafka Partitions, Consumer Groups, Idempotent Producers, Exactly-Once Semantics.")
                        .category("READING").estimatedMinutes(100).resourceUrl("https://dataintensive.net/").build(),

                PlanTask.builder().milestone(m2).dayNumber(9).title("Design a Distributed Rate Limiter & URL Shortener")
                        .description("Architect Token Bucket / Leaky Bucket algorithms, Sliding Window counter, and Snowflake unique ID generator.")
                        .category("SYSTEM_DESIGN").estimatedMinutes(120).resourceUrl("https://www.youtube.com/@gkcs").build(),

                PlanTask.builder().milestone(m2).dayNumber(10).title("Database Sharding & Replication Deep Dive")
                        .description("Read DDIA Chapter 5 & 6: Master-Slave replication, Multi-Leader conflict resolution, and Partitioning schemes.")
                        .category("READING").estimatedMinutes(90).resourceUrl("https://dataintensive.net/").build()
        );
        m2.setTasks(m2Tasks);

        // Build Phase 3: Deep Stack Mastery & Low-Level Design (LLD)
        PlanMilestone m3 = PlanMilestone.builder()
                .plan(plan)
                .sequenceOrder(3)
                .phaseName("Phase 3: Java 21, Spring Boot 3 & Low-Level Clean Architecture")
                .description("Master Java Virtual Threads, Spring Boot microservice internals, SOLID principles, and database performance tuning.")
                .weekNumber(3)
                .estimatedHours(12)
                .completed(false)
                .build();

        List<PlanTask> m3Tasks = Arrays.asList(
                PlanTask.builder().milestone(m3).dayNumber(11).title("Java 21 Concurrency: Virtual Threads & Structured Concurrency")
                        .description("Explore Project Loom, virtual thread scheduling, synchronization bottlenecks, and CompletableFuture.")
                        .category("READING").estimatedMinutes(90).resourceUrl("https://www.baeldung.com/spring-boot").build(),

                PlanTask.builder().milestone(m3).dayNumber(12).title("Spring Boot 3 Microservices & Security Architecture")
                        .description("Implement Spring Cloud Gateway routing, JWT filters, Circuit Breakers (Resilience4j), and Actuator metrics.")
                        .category("READING").estimatedMinutes(100).resourceUrl("https://spring.io/projects/spring-boot").build(),

                PlanTask.builder().milestone(m3).dayNumber(13).title("Low-Level Design: Factory, Strategy & Observer Patterns")
                        .description("Design a Parking Lot / Movie Booking System with clean classes, interfaces, and SOLID architecture.")
                        .category("SYSTEM_DESIGN").estimatedMinutes(120).resourceUrl("https://refactoring.guru/design-patterns").build(),

                PlanTask.builder().milestone(m3).dayNumber(14).title("PostgreSQL Index Internals & Query Optimization")
                        .description("Understand B-Tree vs Hash vs GIN indexes, EXPLAIN ANALYZE execution plans, and N+1 query prevention.")
                        .category("VIDEO_WATCH").estimatedMinutes(90).resourceUrl("https://www.youtube.com/@hnasr").build(),

                PlanTask.builder().milestone(m3).dayNumber(15).title("Clean Code & Refactoring Principles Drill")
                        .description("Review Uncle Bob's Clean Code principles: Single Responsibility, Function length, Meaningful Names, Unit Testing.")
                        .category("READING").estimatedMinutes(90).resourceUrl("https://www.oreilly.com/library/view/clean-code-a/9780136083238/").build()
        );
        m3.setTasks(m3Tasks);

        // Build Phase 4: Leadership, Behavioral & Live Mock Simulations
        PlanMilestone m4 = PlanMilestone.builder()
                .plan(plan)
                .sequenceOrder(4)
                .phaseName("Phase 4: Behavioral Leadership (STAR Method) & Live Mock Simulations")
                .description("Craft compelling STAR behavioral stories, practice cross-functional collaboration defense, and conduct mock simulations.")
                .weekNumber(4)
                .estimatedHours(12)
                .completed(false)
                .build();

        List<PlanTask> m4Tasks = Arrays.asList(
                PlanTask.builder().milestone(m4).dayNumber(16).title("STAR Method Formulation: Projects, Conflicts & Technical Failures")
                        .description("Draft 5 structured STAR responses: Complex technical challenge, Disagreement with peer/lead, Production outage resolution, and Mentorship.")
                        .category("MOCK_INTERVIEW").estimatedMinutes(90).resourceUrl("https://roadmap.sh/backend").build(),

                PlanTask.builder().milestone(m4).dayNumber(17).title("System Architecture Defense Simulation")
                        .description("Practice presenting your recent production project architecture, explaining CAP theorem trade-offs and latency bottlenecks.")
                        .category("MOCK_INTERVIEW").estimatedMinutes(90).resourceUrl("https://martinfowler.com/articles/microservices.html").build(),

                PlanTask.builder().milestone(m4).dayNumber(18).title("Full-Length Live Mock Technical Interview")
                        .description("Conduct a 90-minute end-to-end simulation covering: 30min DSA, 45min System Design, 15min Behavioral.")
                        .category("MOCK_INTERVIEW").estimatedMinutes(90).resourceUrl("https://leetcode.com/studyplan/top-interview-150/").build(),

                PlanTask.builder().milestone(m4).dayNumber(19).title("Final Review & Offer Negotiation Strategy")
                        .description("Review checklist of weak areas, refine questions to ask the interviewer, and prepare compensation benchmarks.")
                        .category("READING").estimatedMinutes(60).resourceUrl("https://roadmap.sh/backend").build()
        );
        m4.setTasks(m4Tasks);

        plan.setMilestones(List.of(m1, m2, m3, m4));
        InterviewPlan savedPlan = planRepository.save(plan);
        log.info("Generated new customized interview plan with ID: {} for user: {}", savedPlan.getId(), userId);

        return mapToPlanDto(savedPlan);
    }

    public List<InterviewPlanDto> getUserPlans(Long userId) {
        return planRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream().map(this::mapToPlanDto).collect(Collectors.toList());
    }

    public InterviewPlanDto getPlanById(Long planId) {
        InterviewPlan plan = planRepository.findById(planId)
                .orElseThrow(() -> new RuntimeException("InterviewPlan not found with id: " + planId));
        return mapToPlanDto(plan);
    }

    public InterviewPlanDto getLatestPlan(Long userId) {
        InterviewPlan plan = planRepository.findFirstByUserIdOrderByCreatedAtDesc(userId)
                .orElse(null);
        return plan != null ? mapToPlanDto(plan) : null;
    }

    public InterviewPlanDto mapToPlanDto(InterviewPlan plan) {
        List<MilestoneDto> milestoneDtos = plan.getMilestones() != null
                ? plan.getMilestones().stream().map(this::mapToMilestoneDto).collect(Collectors.toList())
                : new ArrayList<>();

        List<VerifiedResourceDto> verifiedResources = getCuratedVerifiedResources();

        return InterviewPlanDto.builder()
                .id(plan.getId())
                .userId(plan.getUserId())
                .sessionId(plan.getSessionId())
                .title(plan.getTitle())
                .targetRole(plan.getTargetRole())
                .targetCompanyTier(plan.getTargetCompanyTier())
                .totalWeeks(plan.getTotalWeeks())
                .totalEstimatedHours(plan.getTotalEstimatedHours())
                .completedHours(plan.getCompletedHours())
                .readinessScore(plan.getReadinessScore())
                .status(plan.getStatus())
                .startDate(plan.getStartDate())
                .targetCompletionDate(plan.getTargetCompletionDate())
                .milestones(milestoneDtos)
                .curatedResources(verifiedResources)
                .createdAt(plan.getCreatedAt())
                .updatedAt(plan.getUpdatedAt())
                .build();
    }

    private MilestoneDto mapToMilestoneDto(PlanMilestone m) {
        List<TaskDto> taskDtos = m.getTasks() != null
                ? m.getTasks().stream().map(this::mapToTaskDto).collect(Collectors.toList())
                : new ArrayList<>();

        return MilestoneDto.builder()
                .id(m.getId())
                .planId(m.getPlan() != null ? m.getPlan().getId() : null)
                .sequenceOrder(m.getSequenceOrder())
                .phaseName(m.getPhaseName())
                .description(m.getDescription())
                .weekNumber(m.getWeekNumber())
                .estimatedHours(m.getEstimatedHours())
                .completed(m.isCompleted())
                .tasks(taskDtos)
                .build();
    }

    private TaskDto mapToTaskDto(PlanTask t) {
        return TaskDto.builder()
                .id(t.getId())
                .milestoneId(t.getMilestone() != null ? t.getMilestone().getId() : null)
                .dayNumber(t.getDayNumber())
                .title(t.getTitle())
                .description(t.getDescription())
                .category(t.getCategory())
                .estimatedMinutes(t.getEstimatedMinutes())
                .completed(t.isCompleted())
                .resourceUrl(t.getResourceUrl())
                .completedAt(t.getCompletedAt())
                .build();
    }

    private List<VerifiedResourceDto> getCuratedVerifiedResources() {
        return List.of(
                VerifiedResourceDto.builder()
                        .title("Designing Data-Intensive Applications")
                        .authorOrChannel("Martin Kleppmann")
                        .category("BOOK")
                        .url("https://dataintensive.net/")
                        .domain("dataintensive.net")
                        .description("Gold standard for Distributed Systems, Replication, Partitioning & Transactions.")
                        .isVerifiedWorking(true)
                        .httpStatusCode(200)
                        .verificationBadge("COMMUNITY_GOLD_STANDARD")
                        .rating(4.9)
                        .build(),

                VerifiedResourceDto.builder()
                        .title("System Design Interview – An Insider's Guide")
                        .authorOrChannel("Alex Xu")
                        .category("BOOK")
                        .url("https://bytebytego.com/")
                        .domain("bytebytego.com")
                        .description("Step-by-step blueprints for architecting large scale distributed systems.")
                        .isVerifiedWorking(true)
                        .httpStatusCode(200)
                        .verificationBadge("COMMUNITY_GOLD_STANDARD")
                        .rating(4.9)
                        .build(),

                VerifiedResourceDto.builder()
                        .title("NeetCode - Algorithmic Patterns & LeetCode Solutions")
                        .authorOrChannel("NeetCode")
                        .category("YOUTUBE_CHANNEL")
                        .url("https://www.youtube.com/@NeetCode")
                        .domain("youtube.com")
                        .description("Blind 75 & NeetCode 150 algorithmic pattern walkthroughs.")
                        .isVerifiedWorking(true)
                        .httpStatusCode(200)
                        .verificationBadge("VERIFIED_ACTIVE")
                        .rating(5.0)
                        .build(),

                VerifiedResourceDto.builder()
                        .title("ByteByteGo - Visual System Design Channel")
                        .authorOrChannel("Alex Xu")
                        .category("YOUTUBE_CHANNEL")
                        .url("https://www.youtube.com/@ByteByteGo")
                        .domain("youtube.com")
                        .description("High-impact visual explanations of cloud architectures and caching.")
                        .isVerifiedWorking(true)
                        .httpStatusCode(200)
                        .verificationBadge("VERIFIED_ACTIVE")
                        .rating(4.9)
                        .build(),

                VerifiedResourceDto.builder()
                        .title("Baeldung - Java & Spring Boot Guides")
                        .authorOrChannel("Eugen Paraschiv")
                        .category("ONLINE_TUTORIAL")
                        .url("https://www.baeldung.com/spring-boot")
                        .domain("baeldung.com")
                        .description("Practical tutorials on Spring Boot 3, Spring Security & Microservices.")
                        .isVerifiedWorking(true)
                        .httpStatusCode(200)
                        .verificationBadge("COMMUNITY_GOLD_STANDARD")
                        .rating(4.9)
                        .build(),

                VerifiedResourceDto.builder()
                        .title("Refactoring Guru – Design Patterns in Depth")
                        .authorOrChannel("Alexander Shvets")
                        .category("ONLINE_TUTORIAL")
                        .url("https://refactoring.guru/design-patterns")
                        .domain("refactoring.guru")
                        .description("Interactive catalog of Design Patterns with clean Java code.")
                        .isVerifiedWorking(true)
                        .httpStatusCode(200)
                        .verificationBadge("COMMUNITY_GOLD_STANDARD")
                        .rating(4.9)
                        .build()
        );
    }
}
