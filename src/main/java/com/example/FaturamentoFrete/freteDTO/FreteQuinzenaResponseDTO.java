package com.example.FaturamentoFrete.freteDTO;

import java.math.BigDecimal;
import java.util.List;

// DTO de resposta para a quinzena (ou qualquer período)
public record FreteQuinzenaResponseDTO(List<FreteResponseDTO> fretes,BigDecimal faturamentoTotal) {

}
