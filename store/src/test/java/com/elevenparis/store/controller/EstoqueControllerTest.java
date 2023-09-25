package com.elevenparis.store.controller;

import com.elevenparis.store.dto.EstoqueDTO;
import com.elevenparis.store.entity.Estoque;
import com.elevenparis.store.service.EstoqueService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class EstoqueControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private EstoqueService estoqueService;

    @InjectMocks
    private EstoqueController estoqueController;

    @Autowired
    private ObjectMapper objectMapper;

    private EstoqueDTO estoqueDTO;

    private Estoque estoque;

    @BeforeEach
    void setUp() {
        estoque = new Estoque();
        estoqueDTO = new EstoqueDTO(estoque);
        estoque.setNomeEstoque("Estoque01");
        estoque.setAtivo(true);
    }


    @Test
    void testController() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/estoque"))
                .andExpect(status().isOk());
    }
    @Test
    void testSalvar(){
        estoqueController.cadastrar(estoque);
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
    void testFindByNomeEstoque() throws Exception {
        when(estoqueService.findByNomeEstoque("Estoque01")).thenReturn(estoqueDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/estoque/nome/Estoque01")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
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

}
