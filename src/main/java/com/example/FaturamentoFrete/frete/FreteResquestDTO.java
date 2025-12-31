package com.example.FaturamentoFrete.frete;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FreteResquestDTO(String rota, BigDecimal valorRota, LocalDate data, BigDecimal combustivel, BigDecimal pedagio, BigDecimal faturamento) {
}
