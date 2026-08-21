package com.interviewcraft.plan.controller;

import com.interviewcraft.common.dto.ApiResponse;
import com.interviewcraft.common.dto.InterviewPlanDto;
import com.interviewcraft.common.dto.SkillProfileDto;
import com.interviewcraft.common.dto.TaskDto;
import com.interviewcraft.common.security.JwtUtils;
import com.interviewcraft.plan.service.PlanTrackingService;
import com.interviewcraft.plan.service.RoadmapGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
public class PlanController {

    private final RoadmapGeneratorService roadmapGeneratorService;
    private final PlanTrackingService planTrackingService;
    private final JwtUtils jwtUtils;

    @PostMapping("/generate")
    public ResponseEntity<ApiResponse<InterviewPlanDto>> generatePlan(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestBody(required = false) Map<String, Object> payload) {
        Long userId = 1L;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                userId = jwtUtils.getUserIdFromToken(authHeader.replace("Bearer ", ""));
            } catch (Exception ignored) {}
        }

        String sessionId = payload != null && payload.get("sessionId") != null ? payload.get("sessionId").toString() : "sess-default";
        String targetRole = payload != null && payload.get("targetRole") != null ? payload.get("targetRole").toString() : "Senior Backend Engineer";
        String targetCompanyTier = payload != null && payload.get("targetCompanyTier") != null ? payload.get("targetCompanyTier").toString() : "Tier-1 Tech";

        SkillProfileDto profile = SkillProfileDto.builder()
                .targetRole(targetRole)
                .targetCompanyTier(targetCompanyTier)
                .yearsOfExperience(3)
                .preparationTimeline("30 Days")
                .build();

        InterviewPlanDto plan = roadmapGeneratorService.generateCustomizedPlan(userId, sessionId, profile);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Tailored interview roadmap successfully generated", plan));
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse<List<InterviewPlanDto>>> getUserPlans(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        Long userId = 1L;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                userId = jwtUtils.getUserIdFromToken(authHeader.replace("Bearer ", ""));
            } catch (Exception ignored) {}
        }

        List<InterviewPlanDto> plans = roadmapGeneratorService.getUserPlans(userId);
        return ResponseEntity.ok(ApiResponse.success(plans));
    }

    @GetMapping("/latest")
    public ResponseEntity<ApiResponse<InterviewPlanDto>> getLatestPlan(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        Long userId = 1L;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                userId = jwtUtils.getUserIdFromToken(authHeader.replace("Bearer ", ""));
            } catch (Exception ignored) {}
        }

        InterviewPlanDto plan = roadmapGeneratorService.getLatestPlan(userId);
        if (plan == null) {
            // Auto-generate a starter plan if none exists
            SkillProfileDto defaultProfile = SkillProfileDto.builder()
                    .targetRole("Senior Backend Engineer")
                    .targetCompanyTier("Tier-1 Product")
                    .build();
            plan = roadmapGeneratorService.generateCustomizedPlan(userId, "sess-starter", defaultProfile);
        }
        return ResponseEntity.ok(ApiResponse.success(plan));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InterviewPlanDto>> getPlanById(@PathVariable Long id) {
        InterviewPlanDto plan = roadmapGeneratorService.getPlanById(id);
        return ResponseEntity.ok(ApiResponse.success(plan));
    }

    @PatchMapping("/tasks/{taskId}/toggle")
    public ResponseEntity<ApiResponse<TaskDto>> toggleTask(@PathVariable Long taskId) {
        TaskDto updatedTask = planTrackingService.toggleTask(taskId);
        return ResponseEntity.ok(ApiResponse.success("Task status updated", updatedTask));
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> health() {
        return ResponseEntity.ok(ApiResponse.success("Plan & Roadmap service is healthy"));
    }
}
