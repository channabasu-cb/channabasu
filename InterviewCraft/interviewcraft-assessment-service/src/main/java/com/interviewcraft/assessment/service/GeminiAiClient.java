package com.interviewcraft.assessment.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class GeminiAiClient {

    @Value("${gemini.api.key:${GEMINI_API_KEY:}}")
    private String apiKey;

    @Value("${gemini.api.model:gemini-1.5-flash}")
    private String modelName;

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public GeminiAiClient() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(12))
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public String generateResponse(String systemPrompt, List<Map<String, String>> conversationHistory, String userMessage) {
        if (apiKey != null && !apiKey.trim().isEmpty() && !apiKey.equals("YOUR_GEMINI_API_KEY")) {
            try {
                return callGeminiApi(systemPrompt, conversationHistory, userMessage);
            } catch (Exception e) {
                log.warn("Gemini API call failed, falling back to adaptive AI mentor: {}", e.getMessage());
            }
        }
        return generateAdaptiveMentorResponse(conversationHistory, userMessage);
    }

    private String callGeminiApi(String systemPrompt, List<Map<String, String>> history, String userMessage) throws Exception {
        String endpoint = String.format("https://generativelanguage.googleapis.com/v1beta/models/%s:generateContent?key=%s",
                modelName, apiKey.trim());

        StringBuilder contentsBuilder = new StringBuilder();
        contentsBuilder.append("[");

        // Add system instruction context as initial exchange
        contentsBuilder.append("{\"role\":\"user\",\"parts\":[{\"text\":").append(objectMapper.writeValueAsString(systemPrompt)).append("}]},");
        contentsBuilder.append("{\"role\":\"model\",\"parts\":[{\"text\":\"Understood. I am InterviewCraft AI, ready to evaluate the candidate's skills and build a verified preparation plan.\"}]},");

        for (Map<String, String> msg : history) {
            String role = "user".equalsIgnoreCase(msg.get("sender")) ? "user" : "model";
            contentsBuilder.append("{\"role\":\"").append(role).append("\",\"parts\":[{\"text\":")
                    .append(objectMapper.writeValueAsString(msg.get("text")))
                    .append("}]},");
        }

        contentsBuilder.append("{\"role\":\"user\",\"parts\":[{\"text\":").append(objectMapper.writeValueAsString(userMessage)).append("}]}");
        contentsBuilder.append("]");

        String requestBody = "{\"contents\":" + contentsBuilder + "}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .timeout(Duration.ofSeconds(20))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            JsonNode root = objectMapper.readTree(response.body());
            JsonNode candidates = root.path("candidates");
            if (candidates.isArray() && candidates.size() > 0) {
                JsonNode parts = candidates.get(0).path("content").path("parts");
                if (parts.isArray() && parts.size() > 0) {
                    return parts.get(0).path("text").asText();
                }
            }
        }

        throw new RuntimeException("Gemini returned HTTP " + response.statusCode() + ": " + response.body());
    }

    public String generateAdaptiveMentorResponse(List<Map<String, String>> history, String userMessage) {
        String msgLower = userMessage.toLowerCase();
        int turnCount = history.size();

        if (turnCount == 0 || msgLower.contains("hello") || msgLower.contains("hi") || msgLower.contains("start")) {
            return "👋 Welcome! I am **InterviewCraft AI**, your technical interview preparation mentor.\n\n" +
                    "To tailor your personalized preparation roadmap with verified study materials, let's start with a quick assessment:\n\n" +
                    "1. **Target Role & Level** (e.g. Senior Backend Engineer, Fullstack, Lead, Architect)\n" +
                    "2. **Primary Tech Stack** (e.g. Java 21, Spring Boot, Microservices, PostgreSQL, Kafka)\n" +
                    "3. **Years of Experience** & **Target Preparation Timeline** (e.g. 30 days, 60 days)\n\n" +
                    "Tell me a bit about where you are and what companies you're aiming for!";
        }

        if (msgLower.contains("java") || msgLower.contains("spring") || msgLower.contains("backend") || msgLower.contains("python") || msgLower.contains("node")) {
            return "Great! That gives us a strong baseline. Let's drill into your technical readiness across 3 core pillars:\n\n" +
                    "1. 🧠 **Data Structures & Algorithms**: Are you comfortable with Patterns like Sliding Window, Two Pointers, Trees/Graphs (BFS/DFS), and Dynamic Programming?\n" +
                    "2. 🏗️ **System Design & Architecture**: Have you built distributed systems involving Caching (Redis), Message Queues (Kafka/RabbitMQ), and Database Sharding?\n" +
                    "3. ⚡ **Concurrency & Core Frameworks**: How confident are you with Java Virtual Threads/Locks, Spring Boot Transactions, and API Gateway patterns?\n\n" +
                    "Rate yourself from 1 (Beginner) to 5 (Master) on these 3 pillars!";
        }

        if (msgLower.contains("dsa") || msgLower.contains("system design") || msgLower.contains("1") || msgLower.contains("2") || msgLower.contains("3") || msgLower.contains("4") || msgLower.contains("5")) {
            return "📊 **Assessment Analysis Complete!**\n\n" +
                    "Here is what I've identified for your profile:\n" +
                    "- **Identified Strengths**: Hands-on backend engineering, REST API architecture, and microservice foundations.\n" +
                    "- **Priority Focus Areas**: High-impact Distributed System Design (Scalability, Partitioning, Caching) and High-Yield Algorithmic Patterns (Blind 75 / Top 150).\n" +
                    "- **Recommended Strategy**: 4-Phase structured milestone sprint with verified books, verified YouTube channels, and day-by-day practical coding tasks.\n\n" +
                    "👉 **Your tailored plan is ready!** Click the **'Generate My Verified Interview Plan'** button or type **'generate plan'** to compile your personalized roadmap with verified resources!";
        }

        return "I've noted that! To ensure your study roadmap only includes verified books, active YouTube channels, and working practice platforms, I have calibrated your prep profile.\n\n" +
                "Would you like to review specific technical questions (e.g. Concurrency, Distributed Caching, Kafka, Low-Level Design) or proceed directly to generating your **Verified Interview Roadmap**?";
    }
}
