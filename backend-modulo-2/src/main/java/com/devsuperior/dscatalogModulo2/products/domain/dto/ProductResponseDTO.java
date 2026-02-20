package com.devsuperior.dscatalogModulo2.products.domain.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record ProductResponseDTO(
        Long id,
        String name,
        String description,
        BigDecimal price,
        String imgUrl,
        Instant date,
        List<CategoryResponseDTO> categories
) {
}