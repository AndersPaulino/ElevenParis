package com.elevenparis.store.controller;

import com.elevenparis.store.dto.EstoqueDTO;
import com.elevenparis.store.entity.Estoque;
import com.elevenparis.store.service.EstoqueService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.Null;
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


@WebMvcTest(EstoqueController.class)
class EstoqueControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EstoqueService estoqueService;

    @Autowired
    private EstoqueController estoqueController;

    @Autowired
    private ObjectMapper objectMapper;

    private EstoqueDTO estoqueDTO;

    private Estoque estoque;

    @BeforeEach
    void setUp() {
        estoque = new Estoque();
        estoque.setNomeEstoque("Estoque01");
        this.estoque.setRegistro(LocalDateTime.now());
        this.estoque.setAtivo(true);
        estoqueDTO = new EstoqueDTO(estoque);
    }

    @Test
    void prePersist(){
        assertNotNull(estoqueDTO.getRegistro());
        assertEquals(null, estoqueDTO.getAtualizar());
    }

    @Test
    void testController() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/estoque"))
                .andExpect(status().isOk());
    }
    @Test
    void testSalvar(){
        estoqueController.cadastrar(estoque);

        verify(estoqueService, times(1)).cadastrar(estoque);
    }
    @Test
    void testEstoque() {
        String nomeEstoque = "Estoque";
        Estoque estoque = new Estoque();
        EstoqueDTO resultadoEsperado = new EstoqueDTO(estoque);
        when(estoqueService.findByNomeEstoque(nomeEstoque)).thenReturn(resultadoEsperado);
        EstoqueDTO resultado = estoqueService.findByNomeEstoque(nomeEstoque);
        verify(estoqueService).findByNomeEstoque(nomeEstoque);
        assertEquals(resultadoEsperado, resultado);
    }
    @Test
    void testFindById() throws Exception {
        when(estoqueService.findById(1L)).thenReturn(Optional.ofNullable(estoqueDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/estoque/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testFindByIdNotFound() throws Exception {
        when(estoqueService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/estoque/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    void testFindByNomeEstoque() throws Exception {
        when(estoqueService.findByNomeEstoque("Estoque01")).thenReturn(estoqueDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/estoque/nome/Estoque01")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testFindByNomeEstoqueException() throws Exception {
        when(estoqueService.findByNomeEstoque(anyString())).thenThrow(new RuntimeException("Exceção simulada!"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/estoque/nome/Estoque01")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testFindByNomeEstoqueNaoEncontrado() throws Exception {
        when(estoqueService.findByNomeEstoque("Estoque02")).thenReturn(estoqueDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/estoque/nome/Estoque01")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindByAtivo() throws Exception {
        List<EstoqueDTO> estoqueDTOList = Collections.singletonList(estoqueDTO);
        when(estoqueService.findByAtivo(true)).thenReturn(estoqueDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/estoque/ativo/true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testFindByAtivoNotFound() throws Exception {
        List<EstoqueDTO> estoqueDTOList = Collections.singletonList(estoqueDTO);
        when(estoqueService.findByAtivo(false)).thenReturn(estoqueDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/estoque/ativo/true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindByAtivoException() throws Exception {
        List<EstoqueDTO> estoqueDTOList = Collections.singletonList(estoqueDTO);
        when(estoqueService.findByAtivo(true)).thenThrow(new RuntimeException("Exceção simulada!"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/estoque/ativo/true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testFindByInativo() throws Exception {
        List<EstoqueDTO> estoqueDTOList = Collections.singletonList(estoqueDTO);
        when(estoqueService.findByAtivo(false)).thenReturn(estoqueDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/estoque/ativo/false")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testFindByDiaRegistro() throws Exception {
        List<EstoqueDTO> estoqueDTOList = Collections.singletonList(estoqueDTO);
        when(estoqueService.findByDiaRegistro(LocalDate.now())).thenReturn(estoqueDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/estoque/registro/dia/" + LocalDate.now())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testFindByDiaRegistroNotFound() throws Exception {
        List<EstoqueDTO> estoqueDTOList = Collections.singletonList(estoqueDTO);
        when(estoqueService.findByDiaRegistro(LocalDate.of(2023,05,10))).thenReturn(estoqueDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/estoque/registro/dia/" + LocalDate.now())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindByDiaRegistroException() throws Exception {
        List<EstoqueDTO> estoqueDTOList = Collections.singletonList(estoqueDTO);
        when(estoqueService.findByDiaRegistro(LocalDate.now())).thenThrow(new RuntimeException("Exceção simulada!"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/estoque/registro/dia/" + LocalDate.now())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testFindByDiaAtualizar() throws Exception {
        List<EstoqueDTO> estoqueDTOList = Collections.singletonList(estoqueDTO);
        when(estoqueService.findByDiaAtualizar(LocalDate.now())).thenReturn(estoqueDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/estoque/atualizar/dia/" + LocalDate.now())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testFindByDiaAtualizarNotFound() throws Exception {
        List<EstoqueDTO> estoqueDTOList = Collections.singletonList(estoqueDTO);
        when(estoqueService.findByDiaAtualizar(LocalDate.of(2023,05,10))).thenReturn(estoqueDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/estoque/atualizar/dia/" + LocalDate.now())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    void testFindByDiaAtualizarException() {
        LocalDate dataAtualizar = LocalDate.of(2023, 9, 25);

        when(estoqueService.findByDiaAtualizar(dataAtualizar)).thenThrow(new RuntimeException("Exceção simulada!"));

        ResponseEntity<List<EstoqueDTO>> response = estoqueController.findByDiaAtualizar(dataAtualizar);


        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testFindAll() throws Exception {

        EstoqueDTO estoqueDTO1 = new EstoqueDTO(new Estoque());
        estoqueDTO1.setNomeEstoque("Estoque01");
        estoqueDTO1.setAtivo(true);

        EstoqueDTO estoqueDTO2 = new EstoqueDTO(new Estoque());
        estoqueDTO2.setNomeEstoque("Estoque02");
        estoqueDTO2.setAtivo(false);

        List<EstoqueDTO> estoqueDTOList = Arrays.asList(estoqueDTO1, estoqueDTO2);

        when(estoqueService.findAll()).thenReturn(estoqueDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/estoque")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(estoqueDTOList)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testCadastrarException() {

        when(estoqueController.cadastrar(estoque)).thenThrow(new IllegalArgumentException("Mensagem de erro simulada"));

        ResponseEntity<String> response = estoqueController.cadastrar(estoque);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    
        assertEquals("Mensagem de erro simulada", response.getBody());
    }
    @Test
    void testAtualizarSucesso() {
        Long id = 1L;
        Estoque estoque = new Estoque();

        doNothing().when(estoqueService).atualizar(id, estoque);

        ResponseEntity<String> response = estoqueController.atualizar(id, estoque);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Registro atualizado com sucesso!", response.getBody());

        verify(estoqueService, times(1)).atualizar(id, estoque);
    }

    @Test
    void testAtualizarIllegalArgumentException() {
        Long id = 1L;
        Estoque estoque = new Estoque();

        doThrow(new IllegalArgumentException("Mensagem de erro simulada")).when(estoqueService).atualizar(id, estoque);

        ResponseEntity<String> response = estoqueController.atualizar(id, estoque);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Mensagem de erro simulada", response.getBody());

        verify(estoqueService, times(1)).atualizar(id, estoque);
    }

    @Test
    void testAtualizarException() {
        Long id = 1L;
        Estoque estoque = new Estoque();
        doThrow(new RuntimeException("Erro simulado")).when(estoqueService).atualizar(id, estoque);

        ResponseEntity<String> response = estoqueController.atualizar(id, estoque);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro ao atualizar o registro.", response.getBody());

        verify(estoqueService, times(1)).atualizar(id, estoque);
    }
    @Test
    void testDeleteEstoque() throws Exception {

        EstoqueDTO estoqueDTO1 = new EstoqueDTO(new Estoque());
        estoqueDTO1.setNomeEstoque("Estoque01");
        estoqueDTO1.setAtivo(true);

        EstoqueDTO estoqueDTO2 = new EstoqueDTO(new Estoque());
        estoqueDTO2.setNomeEstoque("Estoque02");
        estoqueDTO2.setAtivo(false);

        List<EstoqueDTO> estoqueDTOList = Arrays.asList(estoqueDTO1, estoqueDTO2);

        when(estoqueService.findAll()).thenReturn(estoqueDTOList);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/estoque/desativar/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void testDeletarIllegalArgumentException() {
        Long id = 1L;

        doThrow(new IllegalArgumentException("Mensagem de erro simulada")).when(estoqueService).deletar(id);

        ResponseEntity<String> response = estoqueController.deletar(id);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Mensagem de erro simulada", response.getBody());

        verify(estoqueService, times(1)).deletar(id);
    }

    @Test
    void testDeletarException() {
        Long id = 1L;

        doThrow(new RuntimeException("Erro simulado")).when(estoqueService).deletar(id);

        ResponseEntity<String> response = estoqueController.deletar(id);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro ao desativar o registro.", response.getBody());

        verify(estoqueService, times(1)).deletar(id);
    }

}
