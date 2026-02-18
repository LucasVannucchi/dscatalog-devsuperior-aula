package com.devsuperior.dscatalog.products.domain.dto;

import com.devsuperior.dscatalog.products.domain.entity.Category;

public record CategoryResponseDTO(
        Long id,
        String name
) {
    public CategoryResponseDTO(Category entity) {
        this(entity.getId(), entity.getName());
    }
}