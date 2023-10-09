package com.elevenparis.store.dto;

import com.elevenparis.store.entity.Movimentacao;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
public class MovimentacaoDTO {
    private Long id;
    private boolean ativo;
    private LocalDateTime registro;
    private LocalDateTime atualizar;
    private BigDecimal totalProduto;
    private BigDecimal valorCompra;
    private BigDecimal valorVenda;

    public MovimentacaoDTO(Movimentacao movimentacao){
        id = movimentacao.getId();
        ativo = movimentacao.isAtivo();
        registro = movimentacao.getRegistro();
        atualizar = movimentacao.getAtualizar();
        totalProduto = movimentacao.getTotalProduto();
        valorCompra = movimentacao.getValorCompra();
        valorVenda = movimentacao.getValorVenda();
    }
    public MovimentacaoDTO(Long id, boolean ativo, LocalDateTime registro, LocalDateTime atualizar, BigDecimal totalProduto, BigDecimal valorCompra, BigDecimal valorVenda) {
        this.id = id;
        this.ativo = ativo;
        this.registro = registro;
        this.atualizar = atualizar;
        this.totalProduto = totalProduto;
        this.valorCompra = valorCompra;
        this.valorVenda = valorVenda;
    }
}
