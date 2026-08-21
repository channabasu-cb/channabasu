package com.interviewcraft.assessment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    @JsonIgnore
    private ChatSession session;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String sender; // "USER" or "AI"

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    private String intent; // "GREETING", "SKILL_PROBING", "TECH_ASSESSMENT", "PLAN_READY", "GENERAL"

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime timestamp;
}
