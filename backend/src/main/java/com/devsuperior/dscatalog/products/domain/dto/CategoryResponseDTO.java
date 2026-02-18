package com.devsuperior.dscatalog.products.domain.dto;

import com.devsuperior.dscatalog.products.domain.entity.Category;

import java.time.Instant;

public record CategoryResponseDTO(
        Long id,
        String name,
        Instant createdAt,
        Instant updatedAt
) {
    public CategoryResponseDTO(Category entity) {
        this(entity.getId(), entity.getName(), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}