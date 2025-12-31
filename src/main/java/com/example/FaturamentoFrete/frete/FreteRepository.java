package com.example.FaturamentoFrete.frete;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FreteRepository extends JpaRepository<Frete, Long>
{
    // Busca todos os fretes dentro de um intervalo de datas
    List<Frete> findByDataBetween(LocalDate inicio, LocalDate fim);



}
