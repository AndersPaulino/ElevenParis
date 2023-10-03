package com.elevenparis.store.dto;

import com.elevenparis.store.entity.Produto;
import com.elevenparis.store.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ProdutoDTOTest {

    private Produto produto;

    private ProdutoDTO produtoDTO;

    @Autowired
    private ProdutoRepository produtoRepository;


    @Test
    void testConstructorWhitProduto(){
        produto = new Produto();
        produto.setNome("Produto Exemplo");

        produtoDTO = new ProdutoDTO(produto);

        assertEquals(produto.getId(), produtoDTO.getId());
        assertEquals(produto.getRegistro(), produtoDTO.getRegistro());
        assertEquals(produto.getAtualizar(), produtoDTO.getAtualizar());
        assertEquals(produto.getTipo(), produtoDTO.getTipo());
        assertEquals(produto.getNome(), produtoDTO.getNome());
        assertEquals(produto.getDescricao(), produtoDTO.getDescricao());

    }

    @Test
    void testConstrutorComParametrosIndividuais(){
        produtoDTO = new ProdutoDTO(1L,true, LocalDateTime.now(),LocalDateTime.now(),"produto01","descricaoprod", null);

        assertEquals(1L,produtoDTO.getId());
        assertEquals(true, produtoDTO.isAtivo());
        assertEquals("produto01", produtoDTO.getNome());
        assertEquals("descricaoprod", produtoDTO.getDescricao());
    }
}
