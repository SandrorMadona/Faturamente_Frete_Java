package com.example.FaturamentoFrete.repository;

import com.example.FaturamentoFrete.entity.Frete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FreteRepository extends JpaRepository<Frete, Long>
{
    // Busca todos os fretes dentro de um intervalo de datas
    <Sort> List<Frete> findByDataBetween(LocalDate inicio, LocalDate fim, Sort sort);





}
