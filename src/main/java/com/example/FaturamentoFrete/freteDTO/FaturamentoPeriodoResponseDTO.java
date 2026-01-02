package com.example.FaturamentoFrete.freteDTO;

import java.math.BigDecimal;
import java.util.List;

// Lista + total
public record FaturamentoPeriodoResponseDTO(
        List<FreteResponseDTO> fretes,
        BigDecimal total
) {}
