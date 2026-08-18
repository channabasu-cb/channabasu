package com.bangaloredairy.catalog.controller;

import com.bangaloredairy.catalog.model.Category;
import com.bangaloredairy.catalog.service.CatalogService;
import com.bangaloredairy.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CategoryController {

    private final CatalogService catalogService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories() {
        List<Category> categories = catalogService.getAllCategories();
        return ResponseEntity.ok(ApiResponse.ok(categories, "Categories retrieved"));
    }
}
