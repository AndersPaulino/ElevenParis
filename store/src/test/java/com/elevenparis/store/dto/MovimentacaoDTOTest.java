package com.elevenparis.store.dto;

import com.elevenparis.store.entity.Movimentacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MovimentacaoDTOTest {

    private Movimentacao movimentacao;

    @BeforeEach
    public void setUp() {
        movimentacao = new Movimentacao();
        movimentacao.setAtivo(true);
        movimentacao.setRegistro(LocalDateTime.now());
        movimentacao.setAtualizar(LocalDateTime.now());
        movimentacao.setEntrada(10);
        movimentacao.setSaida(5);
        movimentacao.setTotalProduto(BigDecimal.valueOf(100.0));
        movimentacao.setValorCompra(BigDecimal.valueOf(50.0));
        movimentacao.setValorVenda(BigDecimal.valueOf(75.0));
        movimentacao.setValorTotal(BigDecimal.valueOf(250.0));
    }

    @Test
    void testConstructorWithMovimentacao() {
        MovimentacaoDTO movimentacaoDTO = new MovimentacaoDTO(movimentacao);
        assertEquals(movimentacao.getId(), movimentacaoDTO.getId());
        assertEquals(movimentacao.isAtivo(), movimentacaoDTO.isAtivo());
        assertEquals(movimentacao.getRegistro(), movimentacaoDTO.getRegistro());
        assertEquals(movimentacao.getAtualizar(), movimentacaoDTO.getAtualizar());
        assertEquals(movimentacao.getEntrada(), movimentacaoDTO.getEntrada());
        assertEquals(movimentacao.getSaida(), movimentacaoDTO.getSaida());
        assertEquals(movimentacao.getTotalProduto(), movimentacaoDTO.getTotalProduto());
        assertEquals(movimentacao.getValorCompra(), movimentacaoDTO.getValorCompra());
        assertEquals(movimentacao.getValorVenda(), movimentacaoDTO.getValorVenda());
        assertEquals(movimentacao.getValorTotal(), movimentacaoDTO.getValorTotal());
    }

    @Test
    void testConstructorWithParameters() {
        Long id = 2L;
        boolean ativo = false;
        LocalDateTime registro = LocalDateTime.now().minusDays(1);
        LocalDateTime atualizar = LocalDateTime.now().minusHours(1);
        int entrada = 20;
        int saida = 15;
        BigDecimal totalProduto = BigDecimal.valueOf(200.0);
        BigDecimal valorCompra = BigDecimal.valueOf(100.0);
        BigDecimal valorVenda = BigDecimal.valueOf(150.0);
        BigDecimal valorTotal = BigDecimal.valueOf(500.0);

        MovimentacaoDTO movimentacaoDTO = new MovimentacaoDTO(id, ativo, registro, atualizar, entrada, saida, totalProduto, valorCompra, valorVenda, valorTotal);
        assertEquals(id, movimentacaoDTO.getId());
        assertEquals(ativo, movimentacaoDTO.isAtivo());
        assertEquals(registro, movimentacaoDTO.getRegistro());
        assertEquals(atualizar, movimentacaoDTO.getAtualizar());
        assertEquals(entrada, movimentacaoDTO.getEntrada());
        assertEquals(saida, movimentacaoDTO.getSaida());
        assertEquals(totalProduto, movimentacaoDTO.getTotalProduto());
        assertEquals(valorCompra, movimentacaoDTO.getValorCompra());
        assertEquals(valorVenda, movimentacaoDTO.getValorVenda());
        assertEquals(valorTotal, movimentacaoDTO.getValorTotal());
    }


}
