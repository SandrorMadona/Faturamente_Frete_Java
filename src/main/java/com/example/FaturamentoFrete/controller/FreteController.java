package com.example.FaturamentoFrete.controller;

import com.example.FaturamentoFrete.freteDTO.*;
import com.example.FaturamentoFrete.service.FreteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("frete")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FreteController {

    @Autowired
    private FreteService freteService;

    // POST - salvar frete
    @PostMapping
    public void saveFrete(@RequestBody FreteResquestDTO data) {
        freteService.save(data);
    }

    // GET - listar todos
    @GetMapping
    public List<FreteResponseDTO> getAll() {
        return freteService.getAll();
    }

    // GET - listar fretes por período
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


    // GET - por intervalo (quinzena ou qualquer período)
    @GetMapping("/quinzena/total")
    public BigDecimal getTotalQuinzena(
            @RequestParam int ano,
            @RequestParam int mes,
            @RequestParam int inicio,
            @RequestParam int fim
    ) {
        return freteService.getTotalQuinzena(ano, mes, inicio, fim);
    }

    // GET - faturamento mensal
    @GetMapping("/mes/total")
    public BigDecimal getTotalMes(
            @RequestParam int ano,
            @RequestParam int mes
    ) {
        return freteService.getTotalMes(ano, mes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFrete(@PathVariable Long id) {
        freteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mes/atual/total")
    public BigDecimal getTotalMesAtual() {
        LocalDate hoje = LocalDate.now();
        return freteService.getTotalMes(hoje.getYear(), hoje.getMonthValue());
    }

}
