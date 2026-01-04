package com.example.FaturamentoFrete.controller;

import com.example.FaturamentoFrete.freteDTO.*;
import com.example.FaturamentoFrete.service.FreteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

// Diz que esta classe é um Controller REST
@RestController

// Prefixo de todas as rotas → /frete
@RequestMapping("frete")
public class FreteController {

    // Injeta o Service (regra de negócio)
    @Autowired
    private FreteService freteService;

    // =========================
    // POST - salvar frete
    // =========================
    @PostMapping
    public FreteResponseDTO saveFrete(@RequestBody FreteRequestDTO data) {
        return freteService.save(data);
    }

    // =========================
    // GET - listar todos os fretes
    // =========================
    @GetMapping
    public List<FreteResponseDTO> getAll() {
        return freteService.getAll();
    }

    // =========================
    // GET - listar por período
    // =========================
    @GetMapping("/periodo")
    public List<FreteResponseDTO> getPorPeriodo(
            @RequestParam String inicio,
            @RequestParam String fim
    ) {
        return freteService.getPorPeriodo(
                LocalDate.parse(inicio),
                LocalDate.parse(fim)
        );
    }

    // =========================
    // GET - total da quinzena
    // =========================
    @GetMapping("/quinzena/total")
    public BigDecimal getTotalQuinzena(
            @RequestParam int ano,
            @RequestParam int mes,
            @RequestParam int inicio,
            @RequestParam int fim
    ) {
        return freteService.getTotalQuinzena(ano, mes, inicio, fim);
    }

    // =========================
    // GET - total do mês informado
    // =========================
    @GetMapping("/mes/total")
    public BigDecimal getTotalMes(
            @RequestParam int ano,
            @RequestParam int mes
    ) {
        return freteService.getTotalMes(ano, mes);
    }

    // =========================
    // GET - total do mês atual
    // =========================
    @GetMapping("/mes/atual/total")
    public BigDecimal getTotalMesAtual() {
        return freteService.getTotalMesAtual();
    }

    // =========================
    // DELETE - remover frete
    // =========================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFrete(@PathVariable Long id) {
        freteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
