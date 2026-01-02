package com.example.FaturamentoFrete.freteDTO;

import com.example.FaturamentoFrete.entity.Frete;

import java.math.BigDecimal;
import java.time.LocalDate;

// DTO de saída (GET lista)
public record FreteResponseDTO(
        Long id,
        String rota,
        BigDecimal valorRota,
        LocalDate data,
        BigDecimal combustivel,
        BigDecimal pedagio,
        BigDecimal faturamento
) {
    public FreteResponseDTO(Frete frete) {
        this(
                frete.getId(),
                frete.getRota(),
                frete.getValorRota(),
                frete.getData(),
                frete.getCombustivel(),
                frete.getPedagio(),
                frete.getFaturamento()
        );
    }
}
