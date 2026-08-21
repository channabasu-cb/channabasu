package com.interviewcraft.resource.controller;

import com.interviewcraft.common.dto.ApiResponse;
import com.interviewcraft.common.dto.LinkVerificationRequest;
import com.interviewcraft.common.dto.LinkVerificationResult;
import com.interviewcraft.common.dto.VerifiedResourceDto;
import com.interviewcraft.resource.service.ResourceCatalogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/resources")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceCatalogService resourceCatalogService;

    @GetMapping("/verified")
    public ResponseEntity<ApiResponse<List<VerifiedResourceDto>>> getVerifiedResources(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String topic,
            @RequestParam(required = false) String query) {
        List<VerifiedResourceDto> resources = resourceCatalogService.getVerifiedResources(category, topic, query);
        return ResponseEntity.ok(ApiResponse.success(resources));
    }

    @PostMapping("/verify-link")
    public ResponseEntity<ApiResponse<LinkVerificationResult>> verifyLink(
            @Valid @RequestBody LinkVerificationRequest request) {
        LinkVerificationResult result = resourceCatalogService.verifyLinkLive(request.getUrl());
        return ResponseEntity.ok(ApiResponse.success("Link verification completed", result));
    }

    @PostMapping("/verify-batch")
    public ResponseEntity<ApiResponse<List<LinkVerificationResult>>> verifyBatch(
            @RequestBody List<String> urls) {
        List<LinkVerificationResult> results = new ArrayList<>();
        for (String url : urls) {
            results.add(resourceCatalogService.verifyLinkLive(url));
        }
        return ResponseEntity.ok(ApiResponse.success("Batch verification completed", results));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<VerifiedResourceDto>> registerResource(
            @Valid @RequestBody VerifiedResourceDto dto) {
        VerifiedResourceDto registered = resourceCatalogService.verifyAndRegisterResource(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Resource verified and registered into catalog", registered));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VerifiedResourceDto>> getResourceById(@PathVariable Long id) {
        VerifiedResourceDto dto = resourceCatalogService.getResourceById(id);
        return ResponseEntity.ok(ApiResponse.success(dto));
    }

    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<String>>> getCategories() {
        List<String> categories = List.of(
                "BOOK",
                "ONLINE_TUTORIAL",
                "YOUTUBE_CHANNEL",
                "DOCUMENTATION",
                "PRACTICE_PLATFORM"
        );
        return ResponseEntity.ok(ApiResponse.success(categories));
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> health() {
        return ResponseEntity.ok(ApiResponse.success("Resource and Link Verifier service is healthy"));
    }
}
