package com.interviewcraft.assessment.controller;

import com.interviewcraft.assessment.service.AssessmentService;
import com.interviewcraft.common.dto.ApiResponse;
import com.interviewcraft.common.dto.ChatMessageDto;
import com.interviewcraft.common.dto.ChatSessionDto;
import com.interviewcraft.common.dto.SkillProfileDto;
import com.interviewcraft.common.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/assessment")
@RequiredArgsConstructor
public class AssessmentController {

    private final AssessmentService assessmentService;
    private final JwtUtils jwtUtils;

    @PostMapping("/session")
    public ResponseEntity<ApiResponse<ChatSessionDto>> startSession(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestBody(required = false) Map<String, String> payload) {
        Long userId = 1L;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                userId = jwtUtils.getUserIdFromToken(authHeader.replace("Bearer ", ""));
            } catch (Exception ignored) {}
        }

        String targetRole = payload != null ? payload.get("targetRole") : null;
        String techStack = payload != null ? payload.get("primaryTechStack") : null;

        ChatSessionDto session = assessmentService.startOrGetSession(userId, targetRole, techStack);
        return ResponseEntity.ok(ApiResponse.success("Assessment chat session active", session));
    }

    @PostMapping("/chat")
    public ResponseEntity<ApiResponse<ChatMessageDto>> chat(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestBody Map<String, String> payload) {
        Long userId = 1L;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                userId = jwtUtils.getUserIdFromToken(authHeader.replace("Bearer ", ""));
            } catch (Exception ignored) {}
        }

        String sessionId = payload.get("sessionId");
        String message = payload.get("message");

        if (sessionId == null || message == null || message.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(ApiResponse.error("SessionId and message are required"));
        }

        ChatMessageDto response = assessmentService.processUserMessage(sessionId, userId, message);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/profile/{sessionId}")
    public ResponseEntity<ApiResponse<SkillProfileDto>> getProfile(@PathVariable String sessionId) {
        SkillProfileDto profile = assessmentService.getExtractedProfile(sessionId);
        return ResponseEntity.ok(ApiResponse.success(profile));
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> health() {
        return ResponseEntity.ok(ApiResponse.success("Assessment AI service is healthy"));
    }
}
