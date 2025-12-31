package com.example.FaturamentoFrete.controller;

import com.example.FaturamentoFrete.frete.*;
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

    // GET - por intervalo (quinzena ou qualquer período)
    @GetMapping("/quinzena")
    public FreteQuinzenaResponseDTO getFretesQuinzena(
            @RequestParam int ano,
            @RequestParam int mes,
            @RequestParam int primeiroDia,
            @RequestParam int ultimoDia
    ) {
        return freteService.getByQuinzena(ano, mes, primeiroDia, ultimoDia);
    }
    // GET - faturamento mensal
    @GetMapping("/mes")
    public FreteQuinzenaResponseDTO getFretesMes(
            @RequestParam int ano,
            @RequestParam int mes
    ) {
        return freteService.getByMes(ano, mes);
    }

    // GET - total mensal (sem lista)
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



}
