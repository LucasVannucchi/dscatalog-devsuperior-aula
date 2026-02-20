package com.devsuperior.dscatalogModulo2.products.domain.dto;

import java.time.Instant;

public record CategoryResponseDTO(
        Long id,
        String name,
        Instant createdAt,
        Instant updatedAt
) {
}