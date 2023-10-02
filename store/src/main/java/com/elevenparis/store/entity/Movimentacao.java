package com.elevenparis.store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_movimentacao", schema = "public")
public class Movimentacao extends AbstractEntity{

    @Getter @Setter
    @Column(name = "entrada", nullable = false)
    private int entrada;

    @Getter @Setter
    @Column(name = "saida", nullable = false)
    private int saida;

    @Getter @Setter
    @Column(name = "totalProduto")
    private BigDecimal totalProduto;

    @Getter @Setter
    @Column(name = "valorCompra")
    private BigDecimal valorCompra;

    @Getter @Setter
    @Column(name = "valorVenda")
    private BigDecimal valorVenda;

    @Getter @Setter
    @Column(name = "valorTotal")
    private BigDecimal valorTotal;
}
