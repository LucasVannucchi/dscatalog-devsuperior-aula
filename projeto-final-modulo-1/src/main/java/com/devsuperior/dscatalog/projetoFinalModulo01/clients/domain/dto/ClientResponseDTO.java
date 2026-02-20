package com.devsuperior.dscatalog.projetoFinalModulo01.clients.domain.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record ClientResponseDTO(
        Long id,
        String name,
        String cpf,
        BigDecimal income,
        Instant birtDate,
        Integer children,
        Instant createdAt,
        Instant updatedAt
) {
}
