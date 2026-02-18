package com.devsuperior.dscatalog.products.domain.dto;

import java.time.Instant;

public record CategoryResponseDTO(
        Long id,
        String name,
        Instant createdAt,
        Instant updatedAt
) {
}