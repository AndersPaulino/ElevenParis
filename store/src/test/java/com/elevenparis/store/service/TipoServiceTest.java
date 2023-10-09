package com.elevenparis.store.service;

import com.elevenparis.store.dto.TipoDTO;
import com.elevenparis.store.entity.Tipo;
import com.elevenparis.store.repository.TipoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TipoServiceTest {

    @Mock
    private TipoRepository tipoRepository;

    private TipoService tipoService;

    private Tipo tipo;

    private TipoDTO tipoDTO;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        tipoService = new TipoService(tipoRepository);
        tipo = new Tipo();
        tipoDTO = new TipoDTO(tipo);
    }

    @Test
    void findByIdNotFound(){
        Long id = 1L;
        when(tipoRepository.findById(id)).thenReturn(Optional.empty());

        Optional<TipoDTO> result = tipoService.findById(id);

        assertFalse(result.isPresent());
    }

    @Test
    void findAllReturnTipoDTOs(){
        List<Tipo> tipoList = Stream.of(new Tipo(), new Tipo()).collect(Collectors.toList());

        when(tipoRepository.findAll()).thenReturn(tipoList);

        List<TipoDTO> result = tipoService.findAll();

        assertEquals(tipoList.size(), result.size());
    }

    @Test
    void findByAtivoReturnTipoDTOs(){
        boolean ativo = true;

        List<Tipo> tipoList = Stream.of(new Tipo(), new Tipo()).collect(Collectors.toList());

        when(tipoRepository.findByAtivo(ativo)).thenReturn(tipoList);

        List<TipoDTO> result = tipoService.findByAtivo(ativo);

        assertEquals(tipoList.size(), result.size());
    }

    @Test
    void testeValidarTipo() {
        tipo.setNameTipo("Estoque1");

        assertDoesNotThrow(() -> tipoService.validarTipo(tipo));
    }

    @Test
    void testValidarTipoIsNull() {
        Tipo tipo2 = new Tipo();

        Tipo tipo3= new Tipo();

        tipo.setNameTipo(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> tipoService.validarTipo(tipo));

        assertEquals("Nome do Tipo não informado!", exception.getMessage());

        tipo2.setNameTipo("");

        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class,
                () -> tipoService.validarTipo(tipo2));

        assertEquals("Nome do Tipo não informado!", exception2.getMessage());

        tipo3.setNameTipo("Estoque@");

        IllegalArgumentException exception3 = assertThrows(IllegalArgumentException.class,
                () -> tipoService.validarTipo(tipo3));

        assertEquals("Nome do Tipo inválido!", exception3.getMessage());

    }

    @Test
    void cadastrarIdInvalid(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> tipoService.cadastrar(tipo));

        assertEquals("Nome do Tipo não informado!", exception.getMessage());

        verify(tipoRepository, never()).save(tipo);
    }
    @Test
    void atualizarShouldUpdateTipoWhenValidTipoAndIdExists() {
        tipo.setNameTipo("Tipo1");

        when(tipoRepository.findById(1L)).thenReturn(Optional.of(new Tipo()));

        assertDoesNotThrow(() -> tipoService.atualizar(1L, tipo));

        verify(tipoRepository, times(1)).save(any(Tipo.class));
    }

    @Test
    void atualizarShouldThrowExceptionWhenInvalidTipo() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> tipoService.atualizar(1L, tipo));

        assertEquals("Nome do Tipo não informado!", exception.getMessage());

        verify(tipoRepository, never()).save(any(Tipo.class));
    }

    @Test
    void atualizarShouldThrowExceptionWhenIdDoesNotExist() {
        tipo.setNameTipo("NovoTipo");

        when(tipoRepository.findById(2L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> tipoService.atualizar(2L, tipo));

        assertEquals("ID de tipo inválido!", exception.getMessage());

        verify(tipoRepository, never()).save(any(Tipo.class));
    }

    @Test
    void deletarShouldDeactivateTipoWhenIdExists() {
        when(tipoRepository.findById(1L)).thenReturn(Optional.of(tipo));

        assertDoesNotThrow(() -> tipoService.deletar(1L));

        assertFalse(tipo.isAtivo());
    }

    @Test
    void cadastrarShouldSaveTipoWhenValidTipo() {
        tipo.setNameTipo("ValidName");

        tipoService.cadastrar(tipo);

        verify(tipoRepository, times(1)).save(tipo);
    }

    @Test
    void deletarShouldThrowExceptionWhenIdDoesNotExist() {
        when(tipoRepository.findById(2L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> tipoService.deletar(2L));

        assertEquals("ID de tipo inválido!", exception.getMessage());
    }

    @Test
    void deletarShouldNotDeactivateTipoWhenAlreadyInactive() {
        Tipo alreadyInactiveTipo = new Tipo();
        alreadyInactiveTipo.setAtivo(false);

        when(tipoRepository.findById(1L)).thenReturn(Optional.of(alreadyInactiveTipo));

        tipoService.deletar(1L);
        assertFalse(alreadyInactiveTipo.isAtivo());
    }



}
