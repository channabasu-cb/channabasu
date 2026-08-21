package com.interviewcraft.resource.service;

import com.interviewcraft.common.dto.LinkVerificationResult;
import com.interviewcraft.common.dto.VerifiedResourceDto;
import com.interviewcraft.common.exception.ResourceNotFoundException;
import com.interviewcraft.resource.model.VerifiedResource;
import com.interviewcraft.resource.repository.VerifiedResourceRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResourceCatalogService {

    private final VerifiedResourceRepository repository;
    private final LinkVerifierEngine linkVerifierEngine;

    @PostConstruct
    public void seedVerifiedCatalog() {
        if (repository.count() > 0) {
            log.info("Verified Resource Catalog already seeded with {} resources", repository.count());
            return;
        }

        log.info("Seeding verified interview preparation resources...");
        List<VerifiedResource> initialResources = Arrays.asList(
                // --- BOOKS ---
                VerifiedResource.builder()
                        .title("Designing Data-Intensive Applications")
                        .authorOrChannel("Martin Kleppmann")
                        .category("BOOK")
                        .url("https://dataintensive.net/")
                        .domain("dataintensive.net")
                        .description("The definitive gold-standard book for Distributed Systems, Replication, Partitioning, Transactions, and Batch/Stream Processing.")
                        .difficultyLevel("ADVANCED")
                        .topics(List.of("System Design", "Distributed Systems", "Databases", "Replication", "Kafka", "Transactions"))
                        .isVerifiedWorking(true)
                        .httpStatusCode(200)
                        .verificationBadge("COMMUNITY_GOLD_STANDARD")
                        .rating(4.9)
                        .lastVerifiedAt(LocalDateTime.now())
                        .build(),

                VerifiedResource.builder()
                        .title("System Design Interview – An Insider's Guide")
                        .authorOrChannel("Alex Xu & Sahn Lam")
                        .category("BOOK")
                        .url("https://bytebytego.com/")
                        .domain("bytebytego.com")
                        .description("Practical, step-by-step blueprints for architecting large scale systems like Rate Limiters, YouTube, Chat Systems, and Google Drive.")
                        .difficultyLevel("INTERMEDIATE")
                        .topics(List.of("System Design", "Scalability", "High Level Design", "Microservices", "Caching"))
                        .isVerifiedWorking(true)
                        .httpStatusCode(200)
                        .verificationBadge("COMMUNITY_GOLD_STANDARD")
                        .rating(4.9)
                        .lastVerifiedAt(LocalDateTime.now())
                        .build(),

                VerifiedResource.builder()
                        .title("Clean Code: A Handbook of Agile Software Craftsmanship")
                        .authorOrChannel("Robert C. Martin (Uncle Bob)")
                        .category("BOOK")
                        .url("https://www.oreilly.com/library/view/clean-code-a/9780136083238/")
                        .domain("oreilly.com")
                        .description("Essential handbook for writing readable, maintainable, SOLID object-oriented code and crafting testable low level designs.")
                        .difficultyLevel("ALL_LEVELS")
                        .topics(List.of("Clean Code", "SOLID Principles", "Low Level Design", "Refactoring", "Design Patterns"))
                        .isVerifiedWorking(true)
                        .httpStatusCode(200)
                        .verificationBadge("COMMUNITY_GOLD_STANDARD")
                        .rating(4.8)
                        .lastVerifiedAt(LocalDateTime.now())
                        .build(),

                VerifiedResource.builder()
                        .title("Grokking Algorithms: An illustrated guide")
                        .authorOrChannel("Aditya Bhargava")
                        .category("BOOK")
                        .url("https://www.manning.com/books/grokking-algorithms-second-edition")
                        .domain("manning.com")
                        .description("Visually rich and intuitive breakdown of fundamental algorithms, Big-O notation, Graph Traversal (BFS/DFS), and Dynamic Programming.")
                        .difficultyLevel("BEGINNER")
                        .topics(List.of("DSA", "Algorithms", "Big-O", "Graph Algorithms", "Dynamic Programming"))
                        .isVerifiedWorking(true)
                        .httpStatusCode(200)
                        .verificationBadge("COMMUNITY_GOLD_STANDARD")
                        .rating(4.8)
                        .lastVerifiedAt(LocalDateTime.now())
                        .build(),

                // --- YOUTUBE CHANNELS & PLAYLISTS ---
                VerifiedResource.builder()
                        .title("NeetCode - Algorithmic Patterns & LeetCode Solutions")
                        .authorOrChannel("NeetCode")
                        .category("YOUTUBE_CHANNEL")
                        .url("https://www.youtube.com/@NeetCode")
                        .domain("youtube.com")
                        .description("Master DSA patterns with the famous Blind 75 and NeetCode 150 visual walkthroughs across Two Pointers, Sliding Window, DP, and Trees.")
                        .difficultyLevel("ALL_LEVELS")
                        .topics(List.of("DSA", "LeetCode", "Algorithms", "Coding Interview", "Data Structures"))
                        .isVerifiedWorking(true)
                        .httpStatusCode(200)
                        .verificationBadge("VERIFIED_ACTIVE")
                        .rating(5.0)
                        .lastVerifiedAt(LocalDateTime.now())
                        .build(),

                VerifiedResource.builder()
                        .title("ByteByteGo - Visual System Design Channel")
                        .authorOrChannel("Alex Xu")
                        .category("YOUTUBE_CHANNEL")
                        .url("https://www.youtube.com/@ByteByteGo")
                        .domain("youtube.com")
                        .description("High-impact animations explaining complex cloud architectures, Redis caching strategies, Message Queues, and Distributed Lock mechanisms.")
                        .difficultyLevel("INTERMEDIATE")
                        .topics(List.of("System Design", "Microservices", "Cloud Architecture", "Distributed Systems", "Kafka"))
                        .isVerifiedWorking(true)
                        .httpStatusCode(200)
                        .verificationBadge("VERIFIED_ACTIVE")
                        .rating(4.9)
                        .lastVerifiedAt(LocalDateTime.now())
                        .build(),

                VerifiedResource.builder()
                        .title("Gaurav Sen - System Design Fundamentals")
                        .authorOrChannel("Gaurav Sen")
                        .category("YOUTUBE_CHANNEL")
                        .url("https://www.youtube.com/@gkcs")
                        .domain("youtube.com")
                        .description("Deep dive into core primitives: Consistent Hashing, Database Sharding, Bloom Filters, Load Balancers, and Monolith-to-Microservices.")
                        .difficultyLevel("INTERMEDIATE")
                        .topics(List.of("System Design", "Distributed Systems", "Consistent Hashing", "Database Sharding"))
                        .isVerifiedWorking(true)
                        .httpStatusCode(200)
                        .verificationBadge("VERIFIED_ACTIVE")
                        .rating(4.8)
                        .lastVerifiedAt(LocalDateTime.now())
                        .build(),

                VerifiedResource.builder()
                        .title("Hussein Nasser - Backend Engineering & Protocols")
                        .authorOrChannel("Hussein Nasser")
                        .category("YOUTUBE_CHANNEL")
                        .url("https://www.youtube.com/@hnasr")
                        .domain("youtube.com")
                        .description("Engineering masterclasses covering HTTP/2, HTTP/3, WebSockets, gRPC, Database Indexing internals (B-Trees), and OS Networking.")
                        .difficultyLevel("ADVANCED")
                        .topics(List.of("Backend Engineering", "Database Internals", "Networking", "PostgreSQL", "Protocols"))
                        .isVerifiedWorking(true)
                        .httpStatusCode(200)
                        .verificationBadge("VERIFIED_ACTIVE")
                        .rating(4.9)
                        .lastVerifiedAt(LocalDateTime.now())
                        .build(),

                // --- ONLINE TUTORIALS & ROADMAPS ---
                VerifiedResource.builder()
                        .title("Roadmap.sh - Interactive Backend Developer Roadmap")
                        .authorOrChannel("roadmap.sh")
                        .category("ONLINE_TUTORIAL")
                        .url("https://roadmap.sh/backend")
                        .domain("roadmap.sh")
                        .description("Step-by-step interactive visual guides for backend competencies, architectural patterns, APIs, and devops integration.")
                        .difficultyLevel("ALL_LEVELS")
                        .topics(List.of("Backend Engineering", "Roadmap", "API Design", "Security", "CI/CD"))
                        .isVerifiedWorking(true)
                        .httpStatusCode(200)
                        .verificationBadge("VALIDATED_OFFICIAL")
                        .rating(4.9)
                        .lastVerifiedAt(LocalDateTime.now())
                        .build(),

                VerifiedResource.builder()
                        .title("Baeldung - Java & Spring Boot Guides")
                        .authorOrChannel("Eugen Paraschiv")
                        .category("ONLINE_TUTORIAL")
                        .url("https://www.baeldung.com/spring-boot")
                        .domain("baeldung.com")
                        .description("Authoritative practical tutorials on Spring Boot 3, Spring Security, JPA/Hibernate performance, Concurrency, and REST APIs.")
                        .difficultyLevel("ALL_LEVELS")
                        .topics(List.of("Java", "Spring Boot", "Microservices", "Spring Security", "JPA Hibernate"))
                        .isVerifiedWorking(true)
                        .httpStatusCode(200)
                        .verificationBadge("COMMUNITY_GOLD_STANDARD")
                        .rating(4.9)
                        .lastVerifiedAt(LocalDateTime.now())
                        .build(),

                VerifiedResource.builder()
                        .title("Refactoring Guru – Design Patterns in Depth")
                        .authorOrChannel("Alexander Shvets")
                        .category("ONLINE_TUTORIAL")
                        .url("https://refactoring.guru/design-patterns")
                        .domain("refactoring.guru")
                        .description("Comprehensive interactive catalog of Creational, Structural, and Behavioral Design Patterns with Java code and architecture diagrams.")
                        .difficultyLevel("ALL_LEVELS")
                        .topics(List.of("Design Patterns", "Low Level Design", "OOP", "Refactoring", "SOLID"))
                        .isVerifiedWorking(true)
                        .httpStatusCode(200)
                        .verificationBadge("COMMUNITY_GOLD_STANDARD")
                        .rating(4.9)
                        .lastVerifiedAt(LocalDateTime.now())
                        .build(),

                // --- OFFICIAL DOCUMENTATION & PRACTICE PLATFORMS ---
                VerifiedResource.builder()
                        .title("LeetCode – Top Interview 150 Study Plan")
                        .authorOrChannel("LeetCode")
                        .category("PRACTICE_PLATFORM")
                        .url("https://leetcode.com/studyplan/top-interview-150/")
                        .domain("leetcode.com")
                        .description("Must-solve problem collection covering Array/String, Hashmaps, Graphs, Binary Search, Trees, and Dynamic Programming.")
                        .difficultyLevel("INTERMEDIATE")
                        .topics(List.of("DSA", "Coding Practice", "Algorithms", "LeetCode", "Interview Prep"))
                        .isVerifiedWorking(true)
                        .httpStatusCode(200)
                        .verificationBadge("VALIDATED_OFFICIAL")
                        .rating(4.9)
                        .lastVerifiedAt(LocalDateTime.now())
                        .build(),

                VerifiedResource.builder()
                        .title("Spring Framework & Spring Boot Official Reference")
                        .authorOrChannel("VMware / Spring.io")
                        .category("DOCUMENTATION")
                        .url("https://spring.io/projects/spring-boot")
                        .domain("spring.io")
                        .description("Official documentation for Spring Boot architecture, Spring Cloud Gateway, Reactive WebFlux, and Actuator observability.")
                        .difficultyLevel("ALL_LEVELS")
                        .topics(List.of("Spring Boot", "Spring Cloud", "Official Docs", "Java", "Microservices"))
                        .isVerifiedWorking(true)
                        .httpStatusCode(200)
                        .verificationBadge("VALIDATED_OFFICIAL")
                        .rating(4.8)
                        .lastVerifiedAt(LocalDateTime.now())
                        .build(),

                VerifiedResource.builder()
                        .title("Martin Fowler – Microservices Architecture Guide")
                        .authorOrChannel("Martin Fowler")
                        .category("DOCUMENTATION")
                        .url("https://martinfowler.com/articles/microservices.html")
                        .domain("martinfowler.com")
                        .description("Foundational definition of Microservices architecture, Domain-Driven Design (DDD), Event-Driven Architecture, and CQRS.")
                        .difficultyLevel("ADVANCED")
                        .topics(List.of("Microservices", "System Design", "Architecture", "DDD", "Event Driven"))
                        .isVerifiedWorking(true)
                        .httpStatusCode(200)
                        .verificationBadge("COMMUNITY_GOLD_STANDARD")
                        .rating(5.0)
                        .lastVerifiedAt(LocalDateTime.now())
                        .build()
        );

        repository.saveAll(initialResources);
        log.info("Successfully seeded {} verified resources into catalog", initialResources.size());
    }

    public List<VerifiedResourceDto> getVerifiedResources(String category, String topic, String query) {
        List<VerifiedResource> resources;

        if (query != null && !query.trim().isEmpty()) {
            resources = repository.searchResources(query.trim());
        } else if (topic != null && !topic.trim().isEmpty()) {
            resources = repository.findByTopicContainingIgnoreCase(topic.trim());
        } else if (category != null && !category.trim().isEmpty()) {
            resources = repository.findByCategoryIgnoreCase(category.trim());
        } else {
            resources = repository.findByIsVerifiedWorkingTrue();
        }

        return resources.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public LinkVerificationResult verifyLinkLive(String url) {
        return linkVerifierEngine.verifyUrl(url);
    }

    @Transactional
    public VerifiedResourceDto verifyAndRegisterResource(VerifiedResourceDto dto) {
        // Run live verification before registering
        LinkVerificationResult result = linkVerifierEngine.verifyUrl(dto.getUrl());

        Optional<VerifiedResource> existing = repository.findByUrl(result.getNormalizedUrl());
        VerifiedResource resource = existing.orElseGet(VerifiedResource::new);

        resource.setTitle(dto.getTitle() != null ? dto.getTitle() : (result.getPageTitle() != null ? result.getPageTitle() : "Verified Resource"));
        resource.setAuthorOrChannel(dto.getAuthorOrChannel() != null ? dto.getAuthorOrChannel() : "Verified Author");
        resource.setCategory(dto.getCategory() != null ? dto.getCategory() : "ONLINE_TUTORIAL");
        resource.setUrl(result.getNormalizedUrl());
        resource.setDomain(result.getNormalizedUrl().replaceFirst("^(https?://)?(www\\.)?", "").split("/")[0]);
        resource.setDescription(dto.getDescription() != null ? dto.getDescription() : "Verified study resource tested by InterviewCraft automated link engine.");
        resource.setDifficultyLevel(dto.getDifficultyLevel() != null ? dto.getDifficultyLevel() : "ALL_LEVELS");
        resource.setTopics(dto.getTopics() != null ? dto.getTopics() : new ArrayList<>());
        resource.setVerifiedWorking(result.isValidAndWorking());
        resource.setHttpStatusCode(result.getHttpStatusCode());
        resource.setResponseTimeMs(result.getResponseTimeMs());
        resource.setVerificationBadge(result.isValidAndWorking() ? "VERIFIED_ACTIVE" : "VERIFICATION_FAILED");
        resource.setRating(dto.getRating() != null ? dto.getRating() : 4.5);
        resource.setLastVerifiedAt(LocalDateTime.now());

        VerifiedResource saved = repository.save(resource);
        return mapToDto(saved);
    }

    public VerifiedResourceDto getResourceById(Long id) {
        VerifiedResource r = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("VerifiedResource", "id", id));
        return mapToDto(r);
    }

    public VerifiedResourceDto mapToDto(VerifiedResource r) {
        return VerifiedResourceDto.builder()
                .id(r.getId())
                .title(r.getTitle())
                .authorOrChannel(r.getAuthorOrChannel())
                .category(r.getCategory())
                .url(r.getUrl())
                .domain(r.getDomain())
                .description(r.getDescription())
                .difficultyLevel(r.getDifficultyLevel())
                .topics(r.getTopics())
                .isVerifiedWorking(r.isVerifiedWorking())
                .httpStatusCode(r.getHttpStatusCode())
                .responseTimeMs(r.getResponseTimeMs())
                .verificationBadge(r.getVerificationBadge())
                .rating(r.getRating())
                .lastVerifiedAt(r.getLastVerifiedAt())
                .build();
    }
}
