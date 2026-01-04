package com.example.FaturamentoFrete.service;

import com.example.FaturamentoFrete.entity.Frete;
import com.example.FaturamentoFrete.freteDTO.FreteRequestDTO;
import com.example.FaturamentoFrete.freteDTO.FreteResponseDTO;
import com.example.FaturamentoFrete.repository.FreteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

// Indica que essa classe contém regras de negócio
@Service
public class FreteService {

    // Injeta o repositório (acesso ao banco)
    @Autowired
    private FreteRepository freteRepository;

    // =========================
    // SALVAR FRETE
    // =========================
    public FreteResponseDTO save(FreteRequestDTO data) {

        // Cria a entidade Frete a partir do DTO
        Frete frete = new Frete(data);

        // Salva no banco
        Frete salvo = freteRepository.save(frete);

        // Retorna DTO de resposta
        return new FreteResponseDTO(salvo);
    }

    // =========================
    // ATUALIZA FRETE
    // =========================
    public FreteResponseDTO update(Long id, FreteRequestDTO data) {

        // 1️⃣ Busca o frete existente
        Frete frete = freteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Frete não encontrado"));

        // 2️⃣ Atualiza os campos permitidos
        frete.setRota(data.rota());
        frete.setValorRota(data.valorRota());
        frete.setCombustivel(data.combustivel());
        frete.setPedagio(data.pedagio());
        frete.setData(data.data());

        // 3️⃣ Recalcula faturamento
        frete.recalcularFaturamento();

        // 4️⃣ Salva atualização
        Frete atualizado = freteRepository.save(frete);

        // 5️⃣ Retorna DTO
        return new FreteResponseDTO(atualizado);
    }


    // =========================
    // DELETAR FRETE
    // =========================
    public void deleteById(Long id) {

        // Verifica se o frete existe
        if (!freteRepository.existsById(id)) {
            throw new RuntimeException("Frete não encontrado com id: " + id);
        }

        // Remove do banco
        freteRepository.deleteById(id);
    }

    // =========================
    // LISTAR TODOS (ordenado)
    // =========================
    public List<FreteResponseDTO> getAll() {

        // Ordenação padrão
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
    // METODO REUTILIZÁVEL
    // =========================
    // Soma apenas o faturamento já salvo no banco
    private BigDecimal calcularTotalPorPeriodo(LocalDate inicio, LocalDate fim) {

        Sort sort = Sort.by("data");

        BigDecimal total = freteRepository
                .findByDataBetween(inicio, fim, sort)
                .stream()
                .map(Frete::getFaturamento)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Garante que nunca retorna null
        return total != null ? total : BigDecimal.ZERO;
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

        return getTotalMes(
                hoje.getYear(),
                hoje.getMonthValue()
        );
    }
}
