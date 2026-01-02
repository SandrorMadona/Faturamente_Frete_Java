package com.example.FaturamentoFrete.entity;

import com.example.FaturamentoFrete.freteDTO.FreteResquestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table(name = "fretes") // indica que essa classe é uma tabela
@Entity (name = "fretes")
@Getter // para indicar ao Lombok gerar metodo de get para todos os itens abaixo
@Setter
@NoArgsConstructor // para declarar um constructor com nenhum argumento
@AllArgsConstructor // este ja declara um constructor com argumento
@EqualsAndHashCode(of = "id") //indicar que o id  é a representação
public class Frete {
    //aqui serao os nomes das colunas que vou querer
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) //indica que essa coluna de id sera a que vai ser a que da nome ao item que vamos colocar
    private Long id;
    private String rota;
    private BigDecimal valorRota;
    private LocalDate data;
    private BigDecimal combustivel;
    private BigDecimal pedagio;
    private BigDecimal faturamento;

    public Frete(FreteResquestDTO data){
        this.rota = data.rota();
        this.valorRota = data.valorRota();
        this.data = data.data();
        this.combustivel = data.combustivel();
        this.pedagio = data.pedagio();
        this.faturamento = calcularFaturamento();
    }

    // regra de negócio isolada
    private BigDecimal calcularFaturamento() {
        return valorRota
                .subtract(combustivel)
                .subtract(pedagio);
    }
}
