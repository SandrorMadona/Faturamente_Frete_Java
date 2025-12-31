package com.example.FaturamentoFrete.frete;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service // indica que esta classe contém regras de negócio
public class FreteService {

    @Autowired
    private FreteRepository freteRepository;

    // =========================
    // SALVAR FRETE
    // =========================
    public void save(FreteResquestDTO data) {
        // A regra de negócio (cálculo do faturamento)
        // já acontece dentro da Entity Frete
        Frete frete = new Frete(data);
        freteRepository.save(frete);
    }

    public void deleteById(Long id) {

        // Verifica se existe antes de deletar
        if (!freteRepository.existsById(id)) {
            throw new RuntimeException("Frete não encontrado com id: " + id);
        }

        freteRepository.deleteById(id);
    }



    // =========================
    // LISTAR TODOS OS FRETES
    // =========================
    public List<FreteResponseDTO> getAll() {
        return freteRepository.findAll()
                .stream()
                .map(FreteResponseDTO::new)
                .toList();
    }

    // =========================
    // LISTAR POR QUINZENA / INTERVALO
    // =========================
    public FreteQuinzenaResponseDTO getByQuinzena(
            int ano,
            int mes,
            int primeiroDia,
            int ultimoDia
    ) {

        // Cria o intervalo de datas com base nos parâmetros recebidos do front
        LocalDate inicio = LocalDate.of(ano, mes, primeiroDia);
        LocalDate fim = LocalDate.of(ano, mes, ultimoDia);

        // Busca os fretes no banco dentro do período
        List<Frete> fretes = freteRepository.findByDataBetween(inicio, fim);

        // Soma o faturamento já calculado e salvo no banco
        BigDecimal faturamentoTotal = calcularTotalFaturamento(fretes);

        // Converte Entity -> DTO
        List<FreteResponseDTO> response = fretes.stream()
                .map(FreteResponseDTO::new)
                .toList();

        // Retorna lista + total
        return new FreteQuinzenaResponseDTO(response, faturamentoTotal);
    }

    // =========================
    // METODO REUTILIZAVEL
    // =========================
    // soma apenas o faturamento já salvo no banco
    public BigDecimal calcularTotalFaturamento(List<Frete> fretes) {

        BigDecimal total = BigDecimal.ZERO;

        for (Frete frete : fretes) {

            // proteção contra dados antigos ou inconsistentes
            if (frete.getFaturamento() != null) {
                total = total.add(frete.getFaturamento());
            }
        }

        return total;
    }


    // =========================
    // FATURAMENTO MENSAL
    // =========================
    public FreteQuinzenaResponseDTO getByMes(int ano, int mes) {

        // Primeiro e último dia do mês
        LocalDate inicio = LocalDate.of(ano, mes, 1);
        LocalDate fim = inicio.withDayOfMonth(inicio.lengthOfMonth());

        // Busca no banco
        List<Frete> fretes = freteRepository.findByDataBetween(inicio, fim);

        // Soma do faturamento já salvo
        BigDecimal faturamentoTotal = calcularTotalFaturamento(fretes);

        // Entity -> DTO
        List<FreteResponseDTO> response = fretes.stream()
                .map(FreteResponseDTO::new)
                .toList();

        return new FreteQuinzenaResponseDTO(response, faturamentoTotal);
    }

    // =========================
    // TOTAL MENSAL (SEM LISTA)
    // =========================
    public BigDecimal getTotalMes(int ano, int mes) {

        LocalDate inicio = LocalDate.of(ano, mes, 1);
        LocalDate fim = inicio.withDayOfMonth(inicio.lengthOfMonth());

        List<Frete> fretes = freteRepository.findByDataBetween(inicio, fim);

        return calcularTotalFaturamento(fretes);
    }

}
