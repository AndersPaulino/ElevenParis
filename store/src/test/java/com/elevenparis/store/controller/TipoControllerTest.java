package com.elevenparis.store.controller;

import com.elevenparis.store.dto.TipoDTO;
import com.elevenparis.store.entity.Tipo;
import com.elevenparis.store.service.TipoService;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TipoController.class)
class TipoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TipoService tipoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TipoController tipoController;

    private Tipo tipo;

    private TipoDTO tipoDTO;

    @BeforeEach
    void setUp(){
        tipo = new Tipo();
        tipo.setNameTipo("Tipo01");
        this.tipo.setAtivo(true);
        this.tipo.setRegistro(LocalDateTime.now());
        tipoDTO = new TipoDTO(tipo);
    }

    @Test
    void tipoControllerTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tipo")).andExpect(status().isOk());
    }

    @Test
    void testSalvar(){
        tipoController.cadastrar(tipo);

        verify(tipoService, times(1)).cadastrar(tipo);
    }

    @Test
    void testFindById() throws Exception {
        when(tipoService.findById(1L)).thenReturn(Optional.ofNullable(tipoDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tipo/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void testFindByIdNotFound() throws Exception {
        when(tipoService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tipo/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindByAtivo() throws Exception {
        List<TipoDTO> tipoDTOList = Collections.singletonList(tipoDTO);
        when(tipoService.findByAtivo(true)).thenReturn(tipoDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tipo/ativo/true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void testFindByAtivoNotFound() throws Exception {
        List<TipoDTO> tipoDTOList = Collections.singletonList(tipoDTO);
        when(tipoService.findByAtivo(false)).thenReturn(tipoDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tipo/ativo/true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    void testFindByAtivoException() throws Exception {
        List<TipoDTO> tipoDTOList = Collections.singletonList(tipoDTO);
        when(tipoService.findByAtivo(true)).thenThrow(new RuntimeException("Exceção simulada!"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tipo/ativo/true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
    @Test
    void testFindByInativo() throws Exception {
        List<TipoDTO> tipoDTOList = Collections.singletonList(tipoDTO);
        when(tipoService.findByAtivo(false)).thenReturn(tipoDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tipo/ativo/false")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void testFindAll() throws Exception {
        TipoDTO tipoDTO1 = new TipoDTO(new Tipo());
        tipoDTO1.setNameTipo("Tipo01");
        tipoDTO1.setAtivo(true);

        TipoDTO tipoDTO2 = new TipoDTO(new Tipo());
        tipoDTO2.setNameTipo("Tipo02");
        tipoDTO2.setAtivo(false);

        List<TipoDTO> tipoDTOList = Arrays.asList(tipoDTO1, tipoDTO2);

        when(tipoService.findAll()).thenReturn(tipoDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tipo")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(tipoDTOList)))
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    void testCadastrarException() {
        when(tipoController.cadastrar(tipo)).thenThrow(new IllegalArgumentException("Mensagem de erro simulada"));

        ResponseEntity<String> response = tipoController.cadastrar(tipo);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        assertEquals("Mensagem de erro simulada", response.getBody());
    }
    @Test
    void testAtualizarSucesso() {
        Long id = 1L;
        Tipo tipo = new Tipo();

        doNothing().when(tipoService).atualizar(id, tipo);

        ResponseEntity<String> response = tipoController.atualizar(id, tipo);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Registro atualizado com sucesso!", response.getBody());

        verify(tipoService, times(1)).atualizar(id, tipo);
    }

    @Test
    void testAtualizarIllegalArgumentException() {
        Long id = 1L;
        Tipo tipo = new Tipo();

        doThrow(new IllegalArgumentException("Mensagem de erro simulada")).when(tipoService).atualizar(id, tipo);

        ResponseEntity<String> response = tipoController.atualizar(id, tipo);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Mensagem de erro simulada", response.getBody());

        verify(tipoService, times(1)).atualizar(id, tipo);
    }

    @Test
    void testAtualizarException() {
        Long id = 1L;
        Tipo tipo = new Tipo();
        doThrow(new RuntimeException("Erro simulado")).when(tipoService).atualizar(id, tipo);

        ResponseEntity<String> response = tipoController.atualizar(id, tipo);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro ao atualizar o registro.", response.getBody());

        verify(tipoService, times(1)).atualizar(id, tipo);
    }

    @Test
    void testDeleteTipo() throws Exception {
        TipoDTO tipoDTO1 = new TipoDTO(new Tipo());
        tipoDTO1.setNameTipo("Tipo01");
        tipoDTO1.setAtivo(true);

        TipoDTO tipoDTO2 = new TipoDTO(new Tipo());
        tipoDTO2.setNameTipo("Tipo02");
        tipoDTO2.setAtivo(false);

        List<TipoDTO> tipoDTOList = Arrays.asList(tipoDTO1, tipoDTO2);

        when(tipoService.findAll()).thenReturn(tipoDTOList);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/tipo/desativar/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testDeletarIllegalArgumentException() {
        Long id = 1L;

        doThrow(new IllegalArgumentException("Mensagem de erro simulada")).when(tipoService).deletar(id);

        ResponseEntity<String> response = tipoController.deletar(id);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Mensagem de erro simulada", response.getBody());

        verify(tipoService, times(1)).deletar(id);
    }

    @Test
    void testDeletarException() {
        Long id = 1L;

        doThrow(new RuntimeException("Erro simulado")).when(tipoService).deletar(id);

        ResponseEntity<String> response = tipoController.deletar(id);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro ao desativar o registro.", response.getBody());

        verify(tipoService, times(1)).deletar(id);
    }

}
