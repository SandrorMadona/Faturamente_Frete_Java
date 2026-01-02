package com.example.FaturamentoFrete.service;

import com.example.FaturamentoFrete.entity.Frete;
import com.example.FaturamentoFrete.freteDTO.FreteRequestDTO;
import com.example.FaturamentoFrete.freteDTO.FreteResponseDTO;
import com.example.FaturamentoFrete.freteDTO.FreteRequestDTO;
import com.example.FaturamentoFrete.repository.FreteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service // camada de regra de negócio
public class FreteService {

    @Autowired
    private FreteRepository freteRepository;

    // =========================
    // SALVAR FRETE
    // =========================
    public void save(FreteRequestDTO data) {
        Frete frete = new Frete(data);
        freteRepository.save(frete);
    }

    // =========================
    // DELETAR FRETE
    // =========================
    public void deleteById(Long id) {
        if (!freteRepository.existsById(id)) {
            throw new RuntimeException("Frete não encontrado com id: " + id);
        }
        freteRepository.deleteById(id);
    }

    // =========================
    // LISTAR TODOS (ordenado)
    // =========================
    public List<FreteResponseDTO> getAll() {
        Sort sort = Sort.by("data").descending()
                .and(Sort.by("id").ascending());

        return freteRepository.findAll(sort)
                .stream()
                .map(FreteResponseDTO::new)
                .toList();
    }

    // =========================
    // LISTAR POR PERÍODO
    // =========================
    public List<FreteResponseDTO> getPorPeriodo(LocalDate inicio, LocalDate fim) {

        Sort sort = Sort.by("data").descending();

        return freteRepository.findByDataBetween(inicio, fim, sort)
                .stream()
                .map(FreteResponseDTO::new)
                .toList();
    }

    // =========================
    // MÉTODO REUTILIZÁVEL
    // =========================
    private BigDecimal calcularTotalPorPeriodo(LocalDate inicio, LocalDate fim) {

        Sort sort = Sort.by("data");

        return freteRepository.findByDataBetween(inicio, fim, sort)
                .stream()
                .map(Frete::getFaturamento)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // =========================
    // FATURAMENTO MENSAL
    // =========================
    public BigDecimal getTotalMes(int ano, int mes) {

        LocalDate inicio = LocalDate.of(ano, mes, 1);
        LocalDate fim = inicio.withDayOfMonth(inicio.lengthOfMonth());

        return calcularTotalPorPeriodo(inicio, fim);
    }

    // =========================
    // FATURAMENTO QUINZENA FIXA
    // =========================
    public BigDecimal getTotalQuinzena(int ano, int mes, int inicioDia, int fimDia) {

        LocalDate inicio = LocalDate.of(ano, mes, inicioDia);
        LocalDate fim = LocalDate.of(ano, mes, fimDia);

        return calcularTotalPorPeriodo(inicio, fim);
    }

    // =========================
    // FATURAMENTO MÊS ATUAL
    // =========================
    public BigDecimal getTotalMesAtual() {
        LocalDate hoje = LocalDate.now();
        return getTotalMes(hoje.getYear(), hoje.getMonthValue());
    }
}
