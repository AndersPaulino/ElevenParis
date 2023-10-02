package com.elevenparis.store.dto;

import com.elevenparis.store.entity.Tipo;
import com.elevenparis.store.repository.TipoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TipoDTOTest {
    private Tipo tipo;
    private TipoDTO tipoDTO;

    @Test
    void testConstructorWithTipo(){
        tipo = new Tipo();
        tipo.setNameTipo("Camisa");
        tipoDTO = new TipoDTO(tipo);

        assertEquals(tipo.getId(), tipoDTO.getId());
        assertEquals(tipo.getNameTipo(), tipoDTO.getNameTipo());
        assertEquals(tipo.getAtualizar(), tipoDTO.getAtualizar());
        assertEquals(tipo.getRegistro(), tipoDTO.getRegistro());
        assertEquals(tipo.isAtivo(), tipoDTO.isAtivo());
    }
    @Test
    void testConstructorWithIndividualParameters(){
        tipoDTO = new TipoDTO(1L, true, LocalDateTime.now(), LocalDateTime.now(), "Calca");

        assertEquals(1L, tipoDTO.getId());
        assertEquals("Calca", tipoDTO.getNameTipo());
        assertEquals(true, tipoDTO.isAtivo());

    }
}
