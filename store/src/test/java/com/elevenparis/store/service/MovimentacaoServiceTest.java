package com.elevenparis.store.service;

import com.elevenparis.store.dto.MovimentacaoDTO;
import com.elevenparis.store.entity.Movimentacao;
import com.elevenparis.store.repository.MovimentacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovimentacaoServiceTest {

    @Mock
    private MovimentacaoRepository movimentacaoRepository;

    private MovimentacaoService movimentacaoService;

    private Movimentacao movimentacao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        movimentacaoService = new MovimentacaoService(movimentacaoRepository);
        movimentacao = new Movimentacao();
    }

    @Test
    void findByIdShouldReturnOptionalEmptyWhenIdNotFound() {
        Long id = 1L;
        when(movimentacaoRepository.findById(id)).thenReturn(Optional.empty());

        Optional<MovimentacaoDTO> result = movimentacaoService.findById(id);

        assertFalse(result.isPresent());
    }

    @Test
    void findAllShouldReturnListOfMovimentacaoDTOs() {
        List<Movimentacao> movimentacaoList = Stream.of(new Movimentacao(), new Movimentacao())
                .collect(Collectors.toList());

        when(movimentacaoRepository.findAll()).thenReturn(movimentacaoList);

        List<MovimentacaoDTO> result = movimentacaoService.findAll();

        assertEquals(movimentacaoList.size(), result.size());
    }

    @Test
    void findByAtivoShouldReturnListOfMovimentacaoDTOs() {
        boolean ativo = true;
        List<Movimentacao> movimentacaoList = Stream.of(new Movimentacao(), new Movimentacao())
                .collect(Collectors.toList());

        when(movimentacaoRepository.findByAtivo(ativo)).thenReturn(movimentacaoList);

        List<MovimentacaoDTO> result = movimentacaoService.findByAtivo(ativo);

        assertEquals(movimentacaoList.size(), result.size());
    }

    @Test
    void findByDiaRegistroShouldReturnListOfMovimentacaoDTOs() {
        LocalDate registro = LocalDate.now();
        List<Movimentacao> movimentacaoList = Stream.of(new Movimentacao(), new Movimentacao())
                .collect(Collectors.toList());

        when(movimentacaoRepository.findByDiaRegistro(registro)).thenReturn(movimentacaoList);

        List<MovimentacaoDTO> result = movimentacaoService.findByDiaRegistro(registro);

        assertEquals(movimentacaoList.size(), result.size());
    }

    @Test
    void findByDiaAtualizarShouldReturnListOfMovimentacaoDTOs() {
        LocalDate atualizar = LocalDate.now();
        List<Movimentacao> movimentacaoList = Stream.of(new Movimentacao(), new Movimentacao())
                .collect(Collectors.toList());

        when(movimentacaoRepository.findByDiaAtualizar(atualizar)).thenReturn(movimentacaoList);

        List<MovimentacaoDTO> result = movimentacaoService.findByDiaAtualizar(atualizar);

        assertEquals(movimentacaoList.size(), result.size());
    }

    @Test
    void validarMovimentacao() {
        movimentacao.setEntrada(5);
        movimentacao.setSaida(2);

        assertDoesNotThrow(() -> movimentacaoService.validarMovimentacao(movimentacao));
    }

    @Test
    void testValidarMovimentacaoIsNullIsEmptyException() throws Exception{
        movimentacao.setEntrada(0);
        movimentacao.setSaida(0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> movimentacaoService.validarMovimentacao(movimentacao));

        assertEquals("Nome De Produto Não Preenchido", exception.getMessage());
    }

    @Test
    void cadastrarShouldSaveMovimentacaoWhenValidMovimentacao() {
        movimentacao.setEntrada(5);
        movimentacao.setSaida(2);

        when(movimentacaoRepository.save(movimentacao)).thenReturn(movimentacao);

        assertDoesNotThrow(() -> movimentacaoService.cadastrar(movimentacao));

        verify(movimentacaoRepository, times(1)).save(movimentacao);
    }


    @Test
    void atualizarShouldUpdateMovimentacaoWhenValidMovimentacaoAndIdExists() {
        movimentacao.setEntrada(5);
        movimentacao.setSaida(2);

        when(movimentacaoRepository.findById(1L)).thenReturn(Optional.of(new Movimentacao()));

        assertDoesNotThrow(() -> movimentacaoService.atualizar(1L, movimentacao));

        verify(movimentacaoRepository, times(1)).save(any(Movimentacao.class));
    }

    @Test
    void atualizarShouldThrowExceptionWhenInvalidMovimentacao() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> movimentacaoService.atualizar(1L, movimentacao));

        assertEquals("ID Invalido!", exception.getMessage());

        verify(movimentacaoRepository, never()).save(any(Movimentacao.class));
    }

    @Test
    void atualizarShouldThrowExceptionWhenIdDoesNotExist() {
        movimentacao.setEntrada(5);
        movimentacao.setSaida(2);

        when(movimentacaoRepository.findById(2L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> movimentacaoService.atualizar(2L, movimentacao));

        assertEquals("ID Invalido!", exception.getMessage());

        verify(movimentacaoRepository, never()).save(any(Movimentacao.class));
    }

    @Test
    void deletarShouldDeactivateMovimentacaoWhenIdExists() {
        when(movimentacaoRepository.findById(1L)).thenReturn(Optional.of(movimentacao));

        assertDoesNotThrow(() -> movimentacaoService.deletar(1L));

        assertFalse(movimentacao.isAtivo());
    }

    @Test
    void deletarShouldThrowExceptionWhenIdDoesNotExist() {
        when(movimentacaoRepository.findById(2L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> movimentacaoService.deletar(2L));

        assertEquals("ID Invalido", exception.getMessage());

        verify(movimentacaoRepository, never()).save(any(Movimentacao.class));
    }
}
