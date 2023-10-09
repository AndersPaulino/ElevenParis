package com.elevenparis.store.service;


import com.elevenparis.store.dto.ProdutoDTO;
import com.elevenparis.store.entity.Produto;
import com.elevenparis.store.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    private ProdutoService produtoService;

    private Produto produto;

    private ProdutoDTO produtoDTO;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        produtoService = new ProdutoService(produtoRepository);
        produto = new Produto();
        produtoDTO = new ProdutoDTO(produto);
    }

    @Test
    void findByidShouldReturnOptionalEmptyWhenIdNotFound(){
        Long id = 1L;
        when(produtoRepository.findById(id)).thenReturn(Optional.empty());

        Optional<ProdutoDTO> result = produtoService.findById(id);

        assertFalse(result.isPresent());
    }

    @Test
    void findByName(){
        String nome = "Produto1";

        Produto produtoSimulado = new Produto();
        Produto produtoSimulado1 = new Produto();
        produtoSimulado1.setNome(nome);
        produtoSimulado.setNome(nome);

        when(produtoRepository.findByNomeProduto(nome)).thenReturn(produtoSimulado);

        ProdutoDTO result = produtoService.findByNome(nome);

        assertNotNull(result);

        assertEquals(nome, result.getNome());
    }

    @Test
    void findAllShhouldReturnListOfProdutoDTOs(){
        List<Produto> produtoList = Stream.of(new Produto(), new Produto()).collect(Collectors.toList());

        when(produtoRepository.findAll()).thenReturn(produtoList);

        List<ProdutoDTO> result = produtoService.findAll();

        assertEquals(produtoList.size(), result.size());
    }

    @Test
    void testValidarProduto() {
        Produto produto = new Produto();
        produto.setNome(null);

        Produto produto2 = new Produto();
        produto2.setNome("Nome Inválido!@#");

        Produto produto3 = new Produto();
        produto3.setNome("");


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            produtoService.validarProduto(produto);
        });

        assertThat(exception.getMessage()).isEqualTo("Nome De Produto Não Preenchido");

        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () -> {
            produtoService.validarProduto(produto2);
        });

        assertThat(exception2.getMessage()).isEqualTo("Nome De Produto Invalido");

        IllegalArgumentException exception3 = assertThrows(IllegalArgumentException.class, () -> {
            produtoService.validarProduto(produto3);
        });

        assertThat(exception3.getMessage()).isEqualTo("Nome De Produto Não Preenchido");
    }
    @Test
    void findByAtivoShouldReturnListOfProdutoDTOs(){
        boolean ativo = true;

        List<Produto> produtoList = Stream.of(new Produto(), new Produto()).collect(Collectors.toList());

        when(produtoRepository.findByAtivo(ativo)).thenReturn(produtoList);

        List<ProdutoDTO> result = produtoService.findByAtivo(ativo);

        assertEquals(produtoList.size(), result.size());


    }
    @Test
    void findByDiaRegistroShouldReturnListOdProdutoDTOs(){
        LocalDate registro = LocalDate.now();

        List<Produto> produtoList = Stream.of(new Produto(), new Produto()).collect(Collectors.toList());

        when(produtoRepository.findByDiaRegistro(registro)).thenReturn(produtoList);

        List<ProdutoDTO> result = produtoService.findByDiaRegistro(registro);

        assertEquals(produtoList.size(), result.size());
    }

    @Test
    void findByDiaAtualizarShouldReturnListOfProdutoDTOs(){
        LocalDate atualizar = LocalDate.now();

        List<Produto> produtoList = Stream.of(new Produto(), new Produto()).collect(Collectors.toList());

        when(produtoRepository.findByDiaAtualizar(atualizar)).thenReturn(produtoList);

        List<ProdutoDTO> result = produtoService.findByDiaAtualizar(atualizar);

        assertEquals(produtoList.size(), result.size());



    }
    @Test
    void testevalidarEstoque(){
        produto.setNome("Produto1");

        assertDoesNotThrow(() -> produtoService.validarProduto(produto));
    }

    @Test
    void TestValidarProdutoIsNullIsEmptyException(){
        Produto produto2 = new Produto();

        Produto produto3 = new Produto();

        produto.setNome(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> produtoService.validarProduto(produto));

        assertEquals("Nome De Produto Não Preenchido", exception.getMessage());

        produto2.setNome("");

        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class,
                () -> produtoService.validarProduto(produto));

        assertEquals("Nome De Produto Não Preenchido", exception.getMessage());

        produto3.setNome("Produto#");

        IllegalArgumentException exception3 = assertThrows(IllegalArgumentException.class,
                () -> produtoService.validarProduto(produto));

        assertEquals("Nome De Produto Não Preenchido", exception.getMessage());
    }

    @Test
    void cadastrarShouldSaveProdutoWhenValidProduto(){
        produto.setNome("Produto1");

        when(produtoRepository.save(produto)).thenReturn(produto);

        assertDoesNotThrow(() -> produtoService.cadastrar(produto));

        verify(produtoRepository, times(1)).save(produto);
    }

    @Test
    void cadastrarShouldThrowExceptionWhenInvalidProduto(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> produtoService.cadastrar(produto));

        assertEquals("Nome De Produto Não Preenchido", exception.getMessage());

        verify(produtoRepository, never()).save(produto);
    }

    @Test
    void atualizarShouldUpdateProdutoWhenValidProdutoAndIdExists(){
        produto.setNome("Produto");

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(new Produto()));

        assertDoesNotThrow(() -> produtoService.atualizar(1L,produto));

        verify(produtoRepository, times(1)).save(any(Produto.class));

    }

    @Test
    void atualizarShouldThrowExceptionWhenInvalidProduto(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> produtoService.atualizar(1L, produto));

        assertEquals("Nome De Produto Não Preenchido", exception.getMessage());

        verify(produtoRepository, never()).save(any(Produto.class));
    }

    @Test
    void atualizarShouldThrowExceptionWhenIdDoesNotExist() {
        produto.setNome("NovoProduto");

        when(produtoRepository.findById(2L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> produtoService.atualizar(2L,produto));

        assertEquals("ID Invalido!", exception.getMessage());

        verify(produtoRepository, never()).save(any(Produto.class));
    }

    @Test
    void deletarShouldDeactivateProdutoWhenIdExists() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        assertDoesNotThrow(() -> produtoService.deletar(1L));

        assertFalse(produto.isAtivo());
    }

    @Test
    void deletarShouldThrowExceptionWhenIdDoesNotExist() {
        when(produtoRepository.findById(2L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> produtoService.deletar(2L));

        assertEquals("ID Invalido", exception.getMessage());

        verify(produtoRepository, never()).save(any(Produto.class));
    }

    @Test
    void validarProdutoShouldThrowExceptionForNullName() {
        produto.setNome(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> produtoService.validarProduto(produto));

        assertEquals("Nome De Produto Não Preenchido", exception.getMessage());
    }

    @Test
    void validarProdutoShouldThrowExceptionForEmptyName() {
        produto.setNome("");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> produtoService.validarProduto(produto));

        assertEquals("Nome De Produto Não Preenchido", exception.getMessage());
    }

    @Test
    void validarProdutoShouldThrowExceptionForInvalidName() {
        produto.setNome("Produto#");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> produtoService.validarProduto(produto));

        assertEquals("Nome De Produto Invalido", exception.getMessage());
    }

    @Test
    void testFindAll() {

        Produto produto1 = new Produto();
        Produto produto2 = new Produto();
        when(produtoRepository.findAll()).thenReturn(Arrays.asList(produto1, produto2));
        List<ProdutoDTO> result = produtoService.findAll();
        assertEquals(2, result.size());
        }

}
