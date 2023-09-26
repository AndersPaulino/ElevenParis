package com.elevenparis.store.service;

import com.elevenparis.store.controller.EstoqueController;
import com.elevenparis.store.dto.EstoqueDTO;
import com.elevenparis.store.entity.Estoque;
import com.elevenparis.store.repository.EstoqueRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.validator.internal.util.Contracts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstoqueServiceTest {
    @Mock
    private EstoqueRepository estoqueRepository;

    private EstoqueService estoqueService;
    
    private Estoque estoque;
    
    private EstoqueDTO estoqueDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        estoqueService = new EstoqueService(estoqueRepository);
        estoque = new Estoque();
        estoqueDTO = new EstoqueDTO(estoque);
    }

    @Test
    void findByIdShouldReturnOptionalEmptyWhenIdNotFound() {
        Long id = 1L;
        when(estoqueRepository.findById(id)).thenReturn(Optional.empty());

        Optional<EstoqueDTO> result = estoqueService.findById(id);

        assertFalse(result.isPresent());
    }


    @Test
    void findAllShouldReturnListOfEstoqueDTOs() {

        List<Estoque> estoqueList = Stream.of(new Estoque(), new Estoque()).collect(Collectors.toList());

        when(estoqueRepository.findAll()).thenReturn(estoqueList);

        List<EstoqueDTO> result = estoqueService.findAll();

        assertEquals(estoqueList.size(), result.size());
        /*assertTrue(result.stream().allMatch(item -> item instanceof EstoqueDTO));*/
    }

    @Test
    void findByNomeEstoqueShouldReturnEstoqueDTO() {
        String nomeEstoque = "Estoque1";

        Estoque estoqueSimulado = new Estoque();
        estoqueSimulado.setNomeEstoque(nomeEstoque);

        when(estoqueRepository.findByNomeEstoque(nomeEstoque)).thenReturn(estoqueSimulado);

        EstoqueDTO result = estoqueService.findByNomeEstoque(nomeEstoque);

        assertNotNull(result);

        assertEquals(nomeEstoque, result.getNomeEstoque());
    }
    @Test
    void findByAtivoShouldReturnListOfEstoqueDTOs() {
        boolean ativo = true;

        List<Estoque> estoqueList = Stream.of(new Estoque(), new Estoque()).collect(Collectors.toList());

        when(estoqueRepository.findByAtivo(ativo)).thenReturn(estoqueList);

        List<EstoqueDTO> result = estoqueService.findByAtivo(ativo);

        assertEquals(estoqueList.size(), result.size());

        /*assertTrue(result.stream().allMatch(item -> item instanceof EstoqueDTO));*/
    }
    @Test
    void findByDiaRegistroShouldReturnListOfEstoqueDTOs() {
        LocalDate registro = LocalDate.now();


        List<Estoque> estoqueList = Stream.of(new Estoque(), new Estoque()).collect(Collectors.toList());

        when(estoqueRepository.findByDiaRegistro(registro)).thenReturn(estoqueList);

        List<EstoqueDTO> result = estoqueService.findByDiaRegistro(registro);

        assertEquals(estoqueList.size(), result.size());

        /*assertTrue(result.stream().allMatch(item -> item instanceof EstoqueDTO));*/
    }

    @Test
    void findByDiaAtualizarShouldReturnListOfEstoqueDTOs() {
        LocalDate atualizar = LocalDate.now();

        List<Estoque> estoqueList = Stream.of(new Estoque(), new Estoque()).collect(Collectors.toList());

        when(estoqueRepository.findByDiaAtualizar(atualizar)).thenReturn(estoqueList);

        List<EstoqueDTO> result = estoqueService.findByDiaAtualizar(atualizar);

        assertEquals(estoqueList.size(), result.size());


       /* assertTrue(result.stream().allMatch(item -> item instanceof EstoqueDTO));*/
    }
    @Test
    void testeValidarEstoque() {
        estoque.setNomeEstoque("Estoque1");

        assertDoesNotThrow(() -> estoqueService.validarEstoque(estoque));
    }

    @Test
    void testValidarEstoqueIsNullIsEmptyException() {
        Estoque estoque2 = new Estoque();

        Estoque estoque3= new Estoque();

        estoque.setNomeEstoque(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> estoqueService.validarEstoque(estoque));

        assertEquals("Nome do Estoque não informado", exception.getMessage());

        estoque2.setNomeEstoque("");

        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class,
                () -> estoqueService.validarEstoque(estoque2));

        assertEquals("Nome do Estoque não informado", exception2.getMessage());

        estoque3.setNomeEstoque("Estoque@");

        IllegalArgumentException exception3 = assertThrows(IllegalArgumentException.class,
                () -> estoqueService.validarEstoque(estoque3));

        assertEquals("Nome do Estoque inválido", exception3.getMessage());

    }

    @Test
    void cadastrarShouldSaveEstoqueWhenValidEstoque() {
        estoque.setNomeEstoque("Estoque1");

        when(estoqueRepository.save(estoque)).thenReturn(estoque);

        assertDoesNotThrow(() -> estoqueService.cadastrar(estoque));

        verify(estoqueRepository, times(1)).save(estoque);
    }

    @Test
    void cadastrarShouldThrowExceptionWhenInvalidEstoque() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> estoqueService.cadastrar(estoque));

        assertEquals("Nome do Estoque não informado", exception.getMessage());

        verify(estoqueRepository, never()).save(estoque);
    }
    @Test
    void atualizarShouldUpdateEstoqueWhenValidEstoqueAndIdExists() {
        estoque.setNomeEstoque("Estoque1");

        when(estoqueRepository.findById(1L)).thenReturn(Optional.of(new Estoque()));

        assertDoesNotThrow(() -> estoqueService.atualizar(1L, estoque));

        verify(estoqueRepository, times(1)).save(any(Estoque.class));
    }

    @Test
    void atualizarShouldThrowExceptionWhenInvalidEstoque() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> estoqueService.atualizar(1L, estoque));

        assertEquals("Nome do Estoque não informado", exception.getMessage());

        verify(estoqueRepository, never()).save(any(Estoque.class));
    }

    @Test
    void atualizarShouldThrowExceptionWhenIdDoesNotExist() {
        estoque.setNomeEstoque("NovoEstoque");

        when(estoqueRepository.findById(2l)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> estoqueService.atualizar(2L, estoque));

        assertEquals("ID de estoque inválido!", exception.getMessage());

        verify(estoqueRepository, never()).save(any(Estoque.class));
    }

    @Test
    void deletarShouldDeactivateEstoqueWhenIdExists() {
        when(estoqueRepository.findById(1L)).thenReturn(Optional.of(estoque));

        assertDoesNotThrow(() -> estoqueService.deletar(1L));

        assertFalse(estoque.isAtivo());
    }

    @Test
    void deletarShouldThrowExceptionWhenIdDoesNotExist() {
        when(estoqueRepository.findById(2L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> estoqueService.deletar(2L));

        assertEquals("ID de estoque inválido!", exception.getMessage());

        verify(estoqueRepository, never()).save(any(Estoque.class));
    }
}
