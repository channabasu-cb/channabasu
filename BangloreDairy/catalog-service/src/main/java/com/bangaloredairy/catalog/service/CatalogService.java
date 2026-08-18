package com.bangaloredairy.catalog.service;

import com.bangaloredairy.catalog.model.Category;
import com.bangaloredairy.catalog.model.Product;
import com.bangaloredairy.catalog.repository.CategoryRepository;
import com.bangaloredairy.catalog.repository.ProductRepository;
import com.bangaloredairy.common.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CatalogService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Cacheable(value = "categories", key = "'all'")
    public List<Category> getAllCategories() {
        return categoryRepository.findAllByOrderByDisplayOrderAsc();
    }

    @Cacheable(value = "products", key = "'all'")
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "products", key = "'category_' + #categoryId")
    public List<ProductDTO> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "products", key = "'subscription_only'")
    public List<ProductDTO> getSubscriptionProducts() {
        return productRepository.findBySupportsDailySubscriptionTrue().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "products", key = "'product_' + #id")
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
        return mapToDTO(product);
    }

    @CacheEvict(value = {"products", "categories"}, allEntries = true)
    public ProductDTO saveProduct(Product product) {
        Product saved = productRepository.save(product);
        return mapToDTO(saved);
    }

    private ProductDTO mapToDTO(Product p) {
        return ProductDTO.builder()
                .id(p.getId())
                .categoryId(p.getCategory() != null ? p.getCategory().getId() : null)
                .categoryName(p.getCategory() != null ? p.getCategory().getName() : "Dairy")
                .name(p.getName())
                .brand(p.getBrand())
                .description(p.getDescription())
                .unitSize(p.getUnitSize())
                .price(p.getPrice())
                .discountedPrice(p.getDiscountedPrice())
                .stockQuantity(p.getStockQuantity())
                .isAvailable(p.getIsAvailable())
                .supportsDailySubscription(p.getSupportsDailySubscription())
                .fatContent(p.getFatContent())
                .snfContent(p.getSnfContent())
                .shelfLifeDays(p.getShelfLifeDays())
                .imageUrl(p.getImageUrl())
                .build();
    }
}
