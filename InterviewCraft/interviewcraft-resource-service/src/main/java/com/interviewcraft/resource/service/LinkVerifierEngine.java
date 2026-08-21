package com.interviewcraft.resource.service;

import com.interviewcraft.common.dto.LinkVerificationResult;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

@Service
@Slf4j
public class LinkVerifierEngine {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36 InterviewCraft/1.0";

    private final HttpClient httpClient;

    private static final Set<String> HIGH_TRUST_DOMAINS = Set.of(
            "github.com", "youtube.com", "youtu.be", "baeldung.com", "roadmap.sh",
            "leetcode.com", "refactoring.guru", "martinfowler.com", "amazon.com",
            "oreilly.com", "geeksforgeeks.org", "spring.io", "postgresql.org",
            "redis.io", "kafka.apache.org", "bytebytego.com", "neetcode.io",
            "coursera.org", "udemy.com", "developer.mozilla.org", "medium.com"
    );

    public LinkVerifierEngine() {
        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(6))
                .build();
    }

    public LinkVerificationResult verifyUrl(String rawUrl) {
        long startTime = System.currentTimeMillis();
        String normalizedUrl = normalizeUrl(rawUrl);

        try {
            URI uri = URI.create(normalizedUrl);
            String domain = extractDomain(uri);

            // Attempt 1: HEAD request
            HttpRequest headRequest = HttpRequest.newBuilder()
                    .uri(uri)
                    .method("HEAD", HttpRequest.BodyPublishers.noBody())
                    .header("User-Agent", USER_AGENT)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .timeout(Duration.ofSeconds(6))
                    .build();

            HttpResponse<Void> headResponse = null;
            try {
                headResponse = httpClient.send(headRequest, HttpResponse.BodyHandlers.discarding());
            } catch (Exception ignored) {
                // Some servers reject HEAD, will fallback to GET
            }

            int statusCode;
            String contentType = "";
            String pageTitle = "";

            if (headResponse != null && headResponse.statusCode() >= 200 && headResponse.statusCode() < 400) {
                statusCode = headResponse.statusCode();
                contentType = headResponse.headers().firstValue("content-type").orElse("text/html");
            } else {
                // Attempt 2: GET request (with first 64KB body read for title extraction)
                HttpRequest getRequest = HttpRequest.newBuilder()
                        .uri(uri)
                        .GET()
                        .header("User-Agent", USER_AGENT)
                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                        .timeout(Duration.ofSeconds(7))
                        .build();

                HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
                statusCode = getResponse.statusCode();
                contentType = getResponse.headers().firstValue("content-type").orElse("text/html");

                if (statusCode >= 200 && statusCode < 400 && contentType.contains("html")) {
                    try {
                        Document doc = Jsoup.parse(getResponse.body());
                        pageTitle = doc.title();
                    } catch (Exception e) {
                        log.debug("Could not parse HTML title for URL: {}", normalizedUrl);
                    }
                }
            }

            long elapsed = System.currentTimeMillis() - startTime;
            boolean isValid = statusCode >= 200 && statusCode < 400;
            String trustScore = calculateTrustScore(domain, isValid);

            return LinkVerificationResult.builder()
                    .url(rawUrl)
                    .normalizedUrl(normalizedUrl)
                    .isValidAndWorking(isValid)
                    .httpStatusCode(statusCode)
                    .statusMessage(isValid ? "HTTP 200 OK - Active & Reachable" : "HTTP " + statusCode + " - Verification Failed")
                    .responseTimeMs(elapsed)
                    .contentType(contentType)
                    .pageTitle(pageTitle.isEmpty() ? null : pageTitle)
                    .sslValid(normalizedUrl.startsWith("https://"))
                    .domainTrustScore(trustScore)
                    .checkedAt(LocalDateTime.now())
                    .build();

        } catch (Exception e) {
            long elapsed = System.currentTimeMillis() - startTime;
            log.warn("Failed to verify URL [{}]: {}", rawUrl, e.getMessage());

            return LinkVerificationResult.builder()
                    .url(rawUrl)
                    .normalizedUrl(normalizedUrl)
                    .isValidAndWorking(false)
                    .httpStatusCode(0)
                    .statusMessage("Connection Error: " + e.getClass().getSimpleName() + " - " + e.getMessage())
                    .responseTimeMs(elapsed)
                    .domainTrustScore("UNREACHABLE")
                    .checkedAt(LocalDateTime.now())
                    .build();
        }
    }

    private String normalizeUrl(String url) {
        if (url == null) return "";
        String trimmed = url.trim();
        if (!trimmed.startsWith("http://") && !trimmed.startsWith("https://")) {
            return "https://" + trimmed;
        }
        return trimmed;
    }

    private String extractDomain(URI uri) {
        String host = uri.getHost();
        if (host == null) return "";
        return host.startsWith("www.") ? host.substring(4) : host;
    }

    private String calculateTrustScore(String domain, boolean isValid) {
        if (!isValid) return "UNREACHABLE";
        for (String highTrust : HIGH_TRUST_DOMAINS) {
            if (domain.toLowerCase().endsWith(highTrust)) {
                return "HIGH";
            }
        }
        return "MEDIUM";
    }
}
