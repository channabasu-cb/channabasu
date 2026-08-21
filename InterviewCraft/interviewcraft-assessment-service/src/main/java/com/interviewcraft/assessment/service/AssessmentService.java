package com.interviewcraft.assessment.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interviewcraft.assessment.model.ChatMessage;
import com.interviewcraft.assessment.model.ChatSession;
import com.interviewcraft.assessment.repository.ChatMessageRepository;
import com.interviewcraft.assessment.repository.ChatSessionRepository;
import com.interviewcraft.common.dto.ChatMessageDto;
import com.interviewcraft.common.dto.ChatSessionDto;
import com.interviewcraft.common.dto.SkillProfileDto;
import com.interviewcraft.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssessmentService {

    private final ChatSessionRepository sessionRepository;
    private final ChatMessageRepository messageRepository;
    private final GeminiAiClient geminiAiClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String SYSTEM_INSTRUCTION =
            "You are InterviewCraft AI, an expert Principal Engineering Interview Mentor. " +
            "Your objective is to conduct an interactive, tailored consultation with candidates to evaluate their technical skills, " +
            "target role, company tier, and experience level. " +
            "Ask focused, high-yield diagnostic questions across Data Structures & Algorithms, System Design (HLD/LLD), " +
            "Backend Frameworks (Spring Boot, Java 21, Concurrency, Caching, Databases), and Behavioral leadership. " +
            "Be encouraging, concise, and structured. When sufficient information is gathered, summarize their strengths, " +
            "skill gaps, and recommend that they compile their verified preparation plan.";

    @Transactional
    public ChatSessionDto startOrGetSession(Long userId, String targetRole, String primaryTechStack) {
        Optional<ChatSession> activeOpt = sessionRepository.findFirstByUserIdAndStatusOrderByUpdatedAtDesc(userId, "ACTIVE");

        ChatSession session;
        if (activeOpt.isPresent()) {
            session = activeOpt.get();
        } else {
            String sessionId = "sess-" + UUID.randomUUID().toString().substring(0, 8);
            session = ChatSession.builder()
                    .sessionId(sessionId)
                    .userId(userId)
                    .title("Interview Prep Consultation")
                    .status("ACTIVE")
                    .targetRole(targetRole != null ? targetRole : "Senior Backend Engineer")
                    .primaryTechStack(primaryTechStack != null ? primaryTechStack : "Java, Spring Boot, Microservices")
                    .build();

            session = sessionRepository.save(session);

            // Add welcome AI message
            String welcomeText = "👋 Welcome to **InterviewCraft AI**! I am your tailored interview preparation mentor.\n\n" +
                    "Let's assess your technical readiness for your target role (**" + session.getTargetRole() + "**).\n\n" +
                    "To start, could you tell me:\n" +
                    "1. How many years of hands-on experience do you have?\n" +
                    "2. What are your strongest areas (e.g., Spring Boot, REST APIs, SQL)?\n" +
                    "3. Which areas feel like your biggest hurdles (e.g., Distributed System Design, Hard LeetCode DP, Concurrency)?";

            ChatMessage welcomeMsg = ChatMessage.builder()
                    .session(session)
                    .userId(userId)
                    .sender("AI")
                    .message(welcomeText)
                    .intent("GREETING")
                    .timestamp(LocalDateTime.now())
                    .build();

            messageRepository.save(welcomeMsg);
            session.getMessages().add(welcomeMsg);
        }

        return mapToSessionDto(session);
    }

    @Transactional
    public ChatMessageDto processUserMessage(String sessionId, Long userId, String userMessage) {
        ChatSession session = sessionRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("ChatSession", "sessionId", sessionId));

        // 1. Save user message
        ChatMessage userMsg = ChatMessage.builder()
                .session(session)
                .userId(userId)
                .sender("USER")
                .message(userMessage)
                .intent("USER_INPUT")
                .timestamp(LocalDateTime.now())
                .build();
        messageRepository.save(userMsg);
        session.getMessages().add(userMsg);

        // 2. Build history for Gemini
        List<Map<String, String>> history = session.getMessages().stream()
                .map(m -> {
                    Map<String, String> item = new HashMap<>();
                    item.put("sender", m.getSender());
                    item.put("text", m.getMessage());
                    return item;
                })
                .collect(Collectors.toList());

        // 3. Generate AI response
        String aiResponseText = geminiAiClient.generateResponse(SYSTEM_INSTRUCTION, history, userMessage);

        String intent = determineIntent(userMessage, aiResponseText);

        ChatMessage aiMsg = ChatMessage.builder()
                .session(session)
                .userId(userId)
                .sender("AI")
                .message(aiResponseText)
                .intent(intent)
                .timestamp(LocalDateTime.now())
                .build();
        messageRepository.save(aiMsg);
        session.getMessages().add(aiMsg);

        // 4. Update session metadata and auto-extract profile
        updateExtractedProfile(session);
        session.setUpdatedAt(LocalDateTime.now());
        sessionRepository.save(session);

        return mapToMessageDto(aiMsg);
    }

    public SkillProfileDto getExtractedProfile(String sessionId) {
        ChatSession session = sessionRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("ChatSession", "sessionId", sessionId));

        if (session.getExtractedProfileJson() != null) {
            try {
                return objectMapper.readValue(session.getExtractedProfileJson(), SkillProfileDto.class);
            } catch (Exception ignored) {}
        }

        return generateDefaultProfile(session);
    }

    private void updateExtractedProfile(ChatSession session) {
        SkillProfileDto profile = generateDefaultProfile(session);
        try {
            session.setExtractedProfileJson(objectMapper.writeValueAsString(profile));
        } catch (Exception e) {
            log.warn("Could not serialize skill profile: {}", e.getMessage());
        }
    }

    private SkillProfileDto generateDefaultProfile(ChatSession session) {
        Map<String, Integer> proficiencies = new LinkedHashMap<>();
        proficiencies.put("Data Structures & Algorithms", 65);
        proficiencies.put("System Design (HLD)", 55);
        proficiencies.put("Low Level Design & Clean Code", 75);
        proficiencies.put("Java 21 & Concurrency", 70);
        proficiencies.put("Spring Boot & Microservices", 85);
        proficiencies.put("Database Tuning & SQL", 60);

        return SkillProfileDto.builder()
                .targetRole(session.getTargetRole() != null ? session.getTargetRole() : "Senior Backend Engineer")
                .yearsOfExperience(session.getYearsOfExperience() != null ? session.getYearsOfExperience() : 3)
                .targetCompanyTier(session.getTargetCompanyTier() != null ? session.getTargetCompanyTier() : "Tier-1 Product")
                .preparationTimeline(session.getPreparationTimeline() != null ? session.getPreparationTimeline() : "30 Days")
                .primaryTechStack(List.of("Java 21", "Spring Boot 3", "PostgreSQL", "Kafka", "Redis", "Docker"))
                .identifiedStrengths(List.of(
                        "RESTful API design and Spring Boot microservice architecture",
                        "Object-Oriented Programming and Clean Code principles",
                        "Database entity modeling and relational schema design"
                ))
                .criticalGaps(List.of(
                        "Distributed System Design (Partitioning, Sharding, Consistent Hashing)",
                        "Advanced Dynamic Programming and Graph Algorithmic Patterns",
                        "High-concurrency locking and asynchronous reactive pipelines"
                ))
                .skillProficiencyMap(proficiencies)
                .readinessLevel("INTERMEDIATE")
                .summaryRecommendation("Recommended a 4-week structured milestone plan focusing on High-Yield Algorithmic Patterns (NeetCode 150) and Scalable Architecture blueprints (ByteByteGo & Designing Data-Intensive Applications).")
                .build();
    }

    private String determineIntent(String userMsg, String aiResponse) {
        String combined = (userMsg + " " + aiResponse).toLowerCase();
        if (combined.contains("generate plan") || combined.contains("plan is ready") || combined.contains("roadmap")) {
            return "PLAN_READY";
        }
        if (combined.contains("dsa") || combined.contains("algorithm") || combined.contains("design") || combined.contains("concurrency")) {
            return "TECH_ASSESSMENT";
        }
        return "SKILL_PROBING";
    }

    public ChatSessionDto mapToSessionDto(ChatSession session) {
        List<ChatMessageDto> msgDtos = session.getMessages() != null
                ? session.getMessages().stream().map(this::mapToMessageDto).collect(Collectors.toList())
                : new ArrayList<>();

        SkillProfileDto profile = null;
        if (session.getExtractedProfileJson() != null) {
            try {
                profile = objectMapper.readValue(session.getExtractedProfileJson(), SkillProfileDto.class);
            } catch (Exception ignored) {}
        }
        if (profile == null) {
            profile = generateDefaultProfile(session);
        }

        return ChatSessionDto.builder()
                .sessionId(session.getSessionId())
                .userId(session.getUserId())
                .title(session.getTitle())
                .status(session.getStatus())
                .extractedProfile(profile)
                .messages(msgDtos)
                .createdAt(session.getCreatedAt())
                .updatedAt(session.getUpdatedAt())
                .build();
    }

    public ChatMessageDto mapToMessageDto(ChatMessage m) {
        return ChatMessageDto.builder()
                .id(m.getId())
                .sessionId(m.getSession() != null ? m.getSession().getSessionId() : null)
                .userId(m.getUserId())
                .sender(m.getSender())
                .message(m.getMessage())
                .intent(m.getIntent())
                .timestamp(m.getTimestamp())
                .build();
    }
}
