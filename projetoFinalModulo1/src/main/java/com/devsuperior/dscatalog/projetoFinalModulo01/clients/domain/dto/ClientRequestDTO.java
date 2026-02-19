package com.devsuperior.dscatalog.projetoFinalModulo01.clients.domain.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record ClientRequestDTO(

        String name,
        String cpf,
        BigDecimal income,
        Instant birthDate,
        Integer children
) {
}
