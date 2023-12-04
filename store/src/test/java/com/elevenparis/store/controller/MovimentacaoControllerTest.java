package com.elevenparis.store.controller;

import com.elevenparis.store.dto.MovimentacaoDTO;
import com.elevenparis.store.dto.ProdutoDTO;
import com.elevenparis.store.entity.Movimentacao;
import com.elevenparis.store.service.MovimentacaoService;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.management.MonitorInfo;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovimentacaoController.class)
class MovimentacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovimentacaoService movimentacaoService;

    @Autowired
    private MovimentacaoController movimentacaoController;

    @Autowired
    private ObjectMapper objectMapper;

    private MovimentacaoDTO movimentacaoDTO;

    private Movimentacao movimentacao;

    @BeforeEach
    void SetUp(){
        movimentacao = new Movimentacao();
        movimentacao.setEntrada(1);
        movimentacao.setSaida(2);
        movimentacao.setValorCompra(BigDecimal.valueOf(100.0));
        movimentacao.setValorVenda(BigDecimal.valueOf(40.00));
        movimentacao.setValorTotal(BigDecimal.valueOf(50.00));
        this.movimentacao.setRegistro(LocalDateTime.now());
        this.movimentacao.setAtivo(true);
        movimentacaoDTO = new MovimentacaoDTO(movimentacao);
    }
    @Test
    void testController() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/movimentacao"));
        mockMvc.perform(get("/api/movimentacao"))
                .andExpect((status().isOk()));
    }
    @Test
    void testSalvar(){
        movimentacaoController.cadastrar(movimentacao);
        verify(movimentacaoService, times(1)).cadastrar(movimentacao);
    }


    @Test
    void testFindByid() throws Exception{
        when(movimentacaoService.findById(1L)).thenReturn(Optional.ofNullable(movimentacaoDTO));

        mockMvc.perform(get("/api/movimentacao/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testFindByIdNotFound() throws Exception{
        when(movimentacaoService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/movimentacao/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }



    @Test
    void testFindByAtivo() throws Exception {
        List<MovimentacaoDTO> movimentacaoDTOList = Collections.singletonList(movimentacaoDTO);
        when(movimentacaoService.findByAtivo(true)).thenReturn(movimentacaoDTOList);

        mockMvc.perform(get("/api/movimentacao/ativo/true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void testFindByAtivoNotFound() throws Exception {
        List<MovimentacaoDTO> movimentacaoDTOList = Collections.singletonList(movimentacaoDTO);
        when(movimentacaoService.findByAtivo(false)).thenReturn(movimentacaoDTOList);

        mockMvc.perform(get("/api/movimentacao/ativo/false")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void testFindByAtivoException() throws Exception {
        when(movimentacaoService.findByAtivo(true)).thenThrow(new RuntimeException("Exceção simulada!"));

        mockMvc.perform(get("/api/movimentacao/ativo/true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
    @Test
    void testFindByDiaRegistro() throws Exception {
        List<MovimentacaoDTO> movimentacaoDTOList = Collections.singletonList(movimentacaoDTO);
        when(movimentacaoService.findByDiaRegistro(LocalDate.now())).thenReturn(movimentacaoDTOList);

        mockMvc.perform(get("/api/movimentacao/registro/dia/" + LocalDate.now())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void testFindByDiaRegistroNotFound() throws Exception {
        List<MovimentacaoDTO> movimentacaoDTOList = Collections.singletonList(movimentacaoDTO);
        when(movimentacaoService.findByDiaRegistro(LocalDate.of(2023, 5, 10))).thenReturn(movimentacaoDTOList);

        mockMvc.perform(get("/api/movimentacao/registro/dia/" + LocalDate.now())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    void testFindByDiaRegistroException() throws Exception {
        when(movimentacaoService.findByDiaRegistro(LocalDate.now())).thenThrow(new RuntimeException("Exceção simulada!"));

        mockMvc.perform(get("/api/movimentacao/registro/dia/" + LocalDate.now())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testFindByDiaAtualizar() throws Exception {
        List<MovimentacaoDTO> movimentacaoDTOList = Collections.singletonList(movimentacaoDTO);
        when(movimentacaoService.findByDiaAtualizar(LocalDate.now())).thenReturn(movimentacaoDTOList);

        mockMvc.perform(get("/api/movimentacao/atualizar/dia/" + LocalDate.now())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testFindByDiaAtualizarNotFound() throws Exception {
        List<MovimentacaoDTO> movimentacaoDTOList = Collections.singletonList(movimentacaoDTO);
        when(movimentacaoService.findByDiaAtualizar(LocalDate.of(2023, 5, 10))).thenReturn(movimentacaoDTOList);

        mockMvc.perform(get("/api/movimentacao/atualizar/dia/" + LocalDate.now())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindByDiaAtualizarException() {
        LocalDate dataAtualizar = LocalDate.of(2023, 9, 25);

        when(movimentacaoService.findByDiaAtualizar(dataAtualizar)).thenThrow(new RuntimeException("Exceção simulada!"));

        ResponseEntity<List<MovimentacaoDTO>> response = movimentacaoController.findByDiaAtualizar(dataAtualizar);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    @Test
    void testFindAll() throws Exception {
        MovimentacaoDTO movimentacaoDTO1 = new MovimentacaoDTO(new Movimentacao());


        MovimentacaoDTO movimentacaoDTO2 = new MovimentacaoDTO(new Movimentacao());


        List<MovimentacaoDTO> movimentacaoDTOList = Arrays.asList(movimentacaoDTO1, movimentacaoDTO2);

        when(movimentacaoService.findAll()).thenReturn(movimentacaoDTOList);

        mockMvc.perform(get("/api/movimentacao")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(movimentacaoDTOList)))
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    void testCadastrarException() {
        when(movimentacaoController.cadastrar(movimentacao)).thenThrow(new IllegalArgumentException("Mensagem de erro simulada"));
        ResponseEntity<String> response = movimentacaoController.cadastrar(movimentacao);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Mensagem de erro simulada", response.getBody());
    }

    @Test
    void testDeletar() throws Exception {
        doNothing().when(movimentacaoService).deletar(1L);

        mockMvc.perform(delete("/api/movimentacao/desativar/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testDeletarNotFound() throws Exception {
        doThrow(new IllegalArgumentException("Registro não encontrado")).when(movimentacaoService).deletar(2L);

        mockMvc.perform(delete("/api/movimentacao/desativar/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeletarException() throws Exception {
        doThrow(new RuntimeException("Exceção simulada")).when(movimentacaoService).deletar(1L);

        mockMvc.perform(delete("/api/movimentacao/desativar/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testAtualizarSuccess() throws Exception {
        Long id = 1L;
        doNothing().when(movimentacaoService).atualizar(id, movimentacao);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/movimentacao/atualizar/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movimentacao)))
                .andExpect(status().isOk())
                .andExpect(content().string("Registro atualizado com sucesso!"));
    }
    @Test
    void testAtualizarBadRequest() {
        Long id = 1L;
        Movimentacao movimentacao = new Movimentacao();
        doThrow(new IllegalArgumentException("Mensagem de erro")).when(movimentacaoService).atualizar(id, movimentacao);

        ResponseEntity<String> responseEntity = movimentacaoController.atualizar(id, movimentacao);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isEqualTo("Mensagem de erro");
    }

    @Test
    void testAtualizarServerError() {
        Long id = 1L;
        Movimentacao movimentacao = new Movimentacao();
        doThrow(new RuntimeException("Erro genérico")).when(movimentacaoService).atualizar(id, movimentacao);

        ResponseEntity<String> responseEntity = movimentacaoController.atualizar(id, movimentacao);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(responseEntity.getBody()).isEqualTo("Erro ao atualizar o registro.");
    }

    @Test
    void testFindByIdWithExistingId() throws Exception {
        MovimentacaoDTO mockDTO = new MovimentacaoDTO(new Movimentacao());
        when(movimentacaoService.findById(anyLong())).thenReturn(Optional.of(mockDTO));

        mockMvc.perform(get("/api/movimentacao/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testFindByIdWithNonExistingId() throws Exception {
        when(movimentacaoService.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/movimentacao/1"))
                .andExpect(status().isNotFound());
    }


    @Test
    void testDeletarWithIllegalArgumentException() throws Exception {
        Long id = 1L;
        doThrow(new IllegalArgumentException("Invalid data")).when(movimentacaoService).deletar(id);

        mockMvc.perform(delete("/api/movimentacao/desativar/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Invalid data")));
    }

    @Test
    void testFindByAtivoWithData() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(movimentacaoController).build();

        List<MovimentacaoDTO> movimentacaoDTOs = Arrays.asList(
                new MovimentacaoDTO(new Movimentacao()),
                new MovimentacaoDTO(new Movimentacao())
        );

        when(movimentacaoService.findByAtivo(true)).thenReturn(movimentacaoDTOs);

        mockMvc.perform(get("/api/movimentacao/ativo/true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testFindByAtivoWithoutData() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(movimentacaoController).build();

        when(movimentacaoService.findByAtivo(true)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/movimentacao/ativo/true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindByAtivoWithException() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(movimentacaoController).build();

        when(movimentacaoService.findByAtivo(true)).thenThrow(new RuntimeException("Some error"));

        mockMvc.perform(get("/api/movimentacao/ativo/true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}

