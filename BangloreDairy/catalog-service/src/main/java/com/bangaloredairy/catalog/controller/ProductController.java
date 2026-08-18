package com.bangaloredairy.catalog.controller;

import com.bangaloredairy.catalog.service.CatalogService;
import com.bangaloredairy.common.dto.ApiResponse;
import com.bangaloredairy.common.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductController {

    private final CatalogService catalogService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getAllProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false, defaultValue = "false") boolean subscriptionOnly) {
        
        List<ProductDTO> products;
        if (categoryId != null) {
            products = catalogService.getProductsByCategory(categoryId);
        } else if (subscriptionOnly) {
            products = catalogService.getSubscriptionProducts();
        } else {
            products = catalogService.getAllProducts();
        }
        return ResponseEntity.ok(ApiResponse.ok(products, "Fetched " + products.size() + " products"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> getProductById(@PathVariable Long id) {
        try {
            ProductDTO product = catalogService.getProductById(id);
            return ResponseEntity.ok(ApiResponse.ok(product, "Product details retrieved"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
