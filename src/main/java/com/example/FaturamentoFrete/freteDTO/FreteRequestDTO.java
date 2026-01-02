package com.example.FaturamentoFrete.freteDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

// DTO usado APENAS para ENTRADA de dados (POST)
public record FreteRequestDTO(
        String rota,
        BigDecimal valorRota,
        LocalDate data,
        BigDecimal combustivel,
        BigDecimal pedagio
) {
}
