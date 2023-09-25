package com.elevenparis.store.dto;

import com.elevenparis.store.entity.Estoque;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class EstoqueDTOTest {

    private Estoque estoque;
    private EstoqueDTO estoqueDTO;
    @Test
    void testConstructorWithEstoque() {

        estoque = new Estoque();
        estoque.setAtivo(true);
        estoque.setRegistro(LocalDateTime.now());
        estoque.setAtualizar(LocalDateTime.now());
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
