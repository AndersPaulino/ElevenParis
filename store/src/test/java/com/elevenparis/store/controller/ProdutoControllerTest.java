package com.elevenparis.store.controller;


import com.elevenparis.store.dto.ProdutoDTO;
import com.elevenparis.store.entity.Produto;
import com.elevenparis.store.service.ProdutoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProdutoController.class)
class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoService produtoService;

    @Autowired
    private ProdutoController produtoController;

    @Autowired
    private ObjectMapper objectMapper;

    private ProdutoDTO produtoDTO;

    private Produto produto;

    @BeforeEach
    void setUp(){
        produto = new Produto();
        produto.setNome("Produto01");
        this.produto.setRegistro(LocalDateTime.now());
        this.produto.setAtivo(true);
        produtoDTO = new ProdutoDTO(produto);
    }

    @Test
    void prePersist(){
        assertNotNull(produtoDTO.getRegistro());
        assertEquals(null, produtoDTO.getAtualizar());
    }

    @Test
    void testController() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/produto"))
                .andExpect(status().isOk());
    }
    @Test
    void testSalvar(){
        produtoController.cadastrar(produto);

        verify(produtoService, times(1)).cadastrar(produto);
    }

    @Test
    void testProduto() {
        String nome = "Produto";
        Produto produto = new Produto();
        ProdutoDTO produtoEsperado = new ProdutoDTO(produto);
        when(produtoService.findByNome(nome)).thenReturn(produtoEsperado);
        ProdutoDTO resultado = produtoService.findByNome(nome);
        verify(produtoService).findByNome(nome);
        assertEquals(produtoEsperado, resultado);
    }

    @Test
    void testFindByid() throws Exception{
        when(produtoService.findById(1L)).thenReturn(Optional.ofNullable(produtoDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/produto/1")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    }
    @Test
    void testFindByIdNotFound() throws Exception{
        when(produtoService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/produto/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    void testFindByNomeProduto() throws Exception{
        when(produtoService.findByNome("Produto1")).thenReturn(produtoDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/produto/nome/Produto1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testFindByNomeProdutoException() throws Exception{
        when(produtoService.findByNome(anyString())).thenThrow(new RuntimeException("Exceção simulada!"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/produto/nome/Produto1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testFindByNomeProdutoNaoEncontrado() throws Exception {
        when(produtoService.findByNome("Produto02")).thenReturn(produtoDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/produto/nome/Produto01")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    void testFindByAtivo() throws Exception {
        List<ProdutoDTO> produtoDTOList = Collections.singletonList(produtoDTO);
        when(produtoService.findByAtivo(true)).thenReturn(produtoDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/produto/ativo/true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testFindByAtivoNotFound() throws Exception {
        List<ProdutoDTO> produtoDTOList = Collections.singletonList(produtoDTO);
        when(produtoService.findByAtivo(false)).thenReturn(produtoDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/produto/ativo/true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    void testFindByAtivoException() throws Exception {
        List<ProdutoDTO> produtoDTOList = Collections.singletonList(produtoDTO);
        when(produtoService.findByAtivo(true)).thenThrow(new RuntimeException("Exceção simulada!"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/produto/ativo/true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testFindByInativo() throws Exception {
        List<ProdutoDTO> produtoDTOList = Collections.singletonList(produtoDTO);
        when(produtoService.findByAtivo(false)).thenReturn(produtoDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/produto/ativo/false")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testFindByDiaRegistro() throws Exception {
        List<ProdutoDTO> produtoDTOList = Collections.singletonList(produtoDTO);
        when(produtoService.findByDiaRegistro(LocalDate.now())).thenReturn(produtoDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/produto/registro/dia/" + LocalDate.now())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testFindByDiaRegistroNotFound() throws Exception {
        List<ProdutoDTO> produtoDTOList = Collections.singletonList(produtoDTO);
        when(produtoService.findByDiaRegistro(LocalDate.of(2023,5,10))).thenReturn(produtoDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/produto/registro/dia/" + LocalDate.now())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindByDiaRegistroException() throws Exception {
        List<ProdutoDTO> produtoDTOList = Collections.singletonList(produtoDTO);
        when(produtoService.findByDiaRegistro(LocalDate.now())).thenThrow(new RuntimeException("Exceção simulada!"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/produto/registro/dia/" + LocalDate.now())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testFindByDiaAtualizar() throws Exception {
        List<ProdutoDTO> produtoDTOList = Collections.singletonList(produtoDTO);
        when(produtoService.findByDiaAtualizar(LocalDate.now())).thenReturn(produtoDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/produto/atualizar/dia/" + LocalDate.now())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testFindByDiaAtualizarNotFound() throws Exception {
        List<ProdutoDTO> produtoDTOList = Collections.singletonList(produtoDTO);
        when(produtoService.findByDiaAtualizar(LocalDate.of(2023,05,10))).thenReturn(produtoDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/produto/atualizar/dia/" + LocalDate.now())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindByDiaAtualizarException() {
        LocalDate dataAtualizar = LocalDate.of(2023, 9, 25);

        when(produtoService.findByDiaAtualizar(dataAtualizar)).thenThrow(new RuntimeException("Exceção simulada!"));

        ResponseEntity<List<ProdutoDTO>> response = produtoController.findByDiaAtualizar(dataAtualizar);


        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testFindAll() throws Exception{
        ProdutoDTO produtoDTO1 = new ProdutoDTO(new Produto());
        produtoDTO1.setNome("Produto01");
        produtoDTO1.setAtivo(true);

        ProdutoDTO produtoDTO2 = new ProdutoDTO(new Produto());
        produtoDTO2.setNome("Produto02");
        produtoDTO2.setAtivo(false);

        List<ProdutoDTO> produtoDTOList = Arrays.asList(produtoDTO1, produtoDTO2);

        when(produtoService.findAll()).thenReturn(produtoDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/produto")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(produtoDTOList)))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void testCadastrarException() {
        when(produtoController.cadastrar(produto)).thenThrow(new IllegalArgumentException("Mensagem de erro simulada"));

        ResponseEntity<String> response = produtoController.cadastrar(produto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        assertEquals("Mensagem de erro simulada", response.getBody());
    }

    @Test
    void testAtualizarSucesso(){
        Long id = 1L;
        Produto produto = new Produto();

        doNothing().when(produtoService).atualizar(id, produto);

        ResponseEntity<String> response = produtoController.atualizar(id, produto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Registro atualizado com sucesso!", response.getBody());

        verify(produtoService, times(1)).atualizar(id, produto);
    }

    @Test
    void testAtualizarIllegalArgumentException(){
        Long id = 1L;
        Produto produto = new Produto();

        doThrow(new IllegalArgumentException("Mensagem de erro simulada")).when(produtoService).atualizar(id, produto);

        ResponseEntity<String> response = produtoController.atualizar(id, produto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Mensagem de erro simulada", response.getBody());

        verify(produtoService, times(1)).atualizar(id, produto);
    }

    @Test
    void testAtualizarException() {
        Long id = 1L;
        Produto produto = new Produto();
        doThrow(new RuntimeException("Erro simulado")).when(produtoService).atualizar(id, produto);

        ResponseEntity<String> response = produtoController.atualizar(id, produto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro ao atualizar o registro.", response.getBody());

        verify(produtoService, times(1)).atualizar(id, produto);
    }

    @Test
    void testDeleteProduto() throws Exception{
        ProdutoDTO produtoDTO1 = new ProdutoDTO(new Produto());
        produtoDTO1.setNome("Produto01");
        produtoDTO1.setAtivo(true);

        ProdutoDTO produtoDTO2 = new ProdutoDTO(new Produto());
        produtoDTO2.setNome("Produto02");
        produtoDTO2.setAtivo(false);


        List<ProdutoDTO> produtoDTOList = Arrays.asList(produtoDTO1, produtoDTO2);

        when(produtoService.findAll()).thenReturn(produtoDTOList);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/produto/desativar/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testDeletarIllegalArgumentException(){
        Long id = 1L;

        doThrow(new IllegalArgumentException("Mensagem de erro simulada")).when(produtoService).deletar(id);

        ResponseEntity<String> response = produtoController.deletar(id);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Mensagem de erro simulada", response.getBody());

        verify(produtoService, times(1)).deletar(id);
    }

    @Test
    void testDeletarException(){
        Long id = 1L;

        doThrow(new RuntimeException("Erro simulado")).when(produtoService).deletar(id);

        ResponseEntity<String> response = produtoController.deletar(id);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro ao desativar o registro", response.getBody());

        verify(produtoService, times(1)).deletar(id);
    }


}
