package com.elevenparis.store.dto;

import com.elevenparis.store.entity.Movimentacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MovimentacaoDTOTest {

    private Movimentacao movimentacao;

    @BeforeEach
    void setUp() {
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
        assertEquals(movimentacao.getTotalProduto(), movimentacaoDTO.getTotalProduto());
        assertEquals(movimentacao.getValorCompra(), movimentacaoDTO.getValorCompra());
        assertEquals(movimentacao.getValorVenda(), movimentacaoDTO.getValorVenda());
    }

    /*@Test
    void testConstructorWithParameters() {
        Long id = 2L;
        boolean ativo = false;
        LocalDateTime registro = LocalDateTime.now().minusDays(1);
        LocalDateTime atualizar = LocalDateTime.now().minusHours(1);
        BigDecimal totalProduto = BigDecimal.valueOf(200.0);
        BigDecimal valorCompra = BigDecimal.valueOf(100.0);
        BigDecimal valorVenda = BigDecimal.valueOf(150.0);

        MovimentacaoDTO movimentacaoDTO = new MovimentacaoDTO(id, ativo, registro, atualizar, totalProduto, valorCompra, valorVenda);
        assertEquals(id, movimentacaoDTO.getId());
        assertEquals(ativo, movimentacaoDTO.isAtivo());
        assertEquals(registro, movimentacaoDTO.getRegistro());
        assertEquals(atualizar, movimentacaoDTO.getAtualizar());
        assertEquals(totalProduto, movimentacaoDTO.getTotalProduto());
        assertEquals(valorCompra, movimentacaoDTO.getValorCompra());
        assertEquals(valorVenda, movimentacaoDTO.getValorVenda());
    }*/
    @Test
    void testGettersAndSetters() {
        movimentacao = new Movimentacao();
        movimentacao.setAtivo(true);
        movimentacao.setRegistro(LocalDateTime.now());
        movimentacao.setAtualizar(LocalDateTime.now().plusDays(1));
        movimentacao.setTotalProduto(BigDecimal.valueOf(100.0));
        movimentacao.setValorCompra(BigDecimal.valueOf(50.0));
        movimentacao.setValorVenda(BigDecimal.valueOf(80.0));

        MovimentacaoDTO movimentacaoDTO = new MovimentacaoDTO(movimentacao);

        assertTrue(movimentacaoDTO.isAtivo());
        assertNotNull(movimentacaoDTO.getRegistro());
        assertNotNull(movimentacaoDTO.getAtualizar());
        assertEquals(BigDecimal.valueOf(100.0), movimentacaoDTO.getTotalProduto());
        assertEquals(BigDecimal.valueOf(50.0), movimentacaoDTO.getValorCompra());
        assertEquals(BigDecimal.valueOf(80.0), movimentacaoDTO.getValorVenda());

        movimentacaoDTO.setId(2L);
        movimentacaoDTO.setAtivo(false);
        movimentacaoDTO.setRegistro(LocalDateTime.now().minusDays(1));
        movimentacaoDTO.setAtualizar(LocalDateTime.now().plusDays(2));
        movimentacaoDTO.setTotalProduto(BigDecimal.valueOf(150.0));
        movimentacaoDTO.setValorCompra(BigDecimal.valueOf(75.0));
        movimentacaoDTO.setValorVenda(BigDecimal.valueOf(120.0));

        assertEquals(2L, movimentacaoDTO.getId());
        assertFalse(movimentacaoDTO.isAtivo());
        assertNotNull(movimentacaoDTO.getRegistro());
        assertNotNull(movimentacaoDTO.getAtualizar());
        assertEquals(BigDecimal.valueOf(150.0), movimentacaoDTO.getTotalProduto());
        assertEquals(BigDecimal.valueOf(75.0), movimentacaoDTO.getValorCompra());
        assertEquals(BigDecimal.valueOf(120.0), movimentacaoDTO.getValorVenda());
    }
}
