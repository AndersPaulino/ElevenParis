package com.elevenparis.store.dto;


import com.elevenparis.store.entity.Estoque;
import com.elevenparis.store.repository.EstoqueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EstoqueDTOTest {
    private Estoque estoque;
    private EstoqueDTO estoqueDTO;

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Test
    void testConstructorWithEstoque() {

        estoque = new Estoque();
        estoque.setNomeEstoque("Estoque de Exemplo");

        estoqueDTO = new EstoqueDTO(estoque);


        assertEquals(estoque.getId(), estoqueDTO.getId());
        assertEquals(estoque.getNomeEstoque(), estoqueDTO.getNomeEstoque());
        assertEquals(estoque.getAtualizar(), estoqueDTO.getAtualizar());
        assertEquals(estoque.getRegistro(), estoqueDTO.getRegistro());
        assertEquals(estoque.isAtivo(), estoqueDTO.isAtivo());
    }

    @Test
    void testConstructorWithIndividualParameters() {

        estoqueDTO = new EstoqueDTO(1L, true, LocalDateTime.now(), LocalDateTime.now(), "Estoque01");

        assertEquals(1L, estoqueDTO.getId());
        assertEquals("Estoque01", estoqueDTO.getNomeEstoque());
        assertEquals(true, estoqueDTO.isAtivo());
    }
}
