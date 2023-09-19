package com.elevenparis.store.controller.testeController;

import com.elevenparis.store.controller.EstoqueController;
import com.elevenparis.store.dto.EstoqueDTO;
import com.elevenparis.store.entity.Estoque;
import com.elevenparis.store.service.EstoqueService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.swing.tree.ExpandVetoException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@WebMvcTest(EstoqueController.class)
public class EstoqueTesteController {
    private MockMvc mockMvc;
    @Autowired
    public EstoqueTesteController(MockMvc mockMvc){
        this.mockMvc = mockMvc;
    }
    @MockBean
    private EstoqueService estoqueService;

    @Autowired
    private ObjectMapper objectMapper;

    private EstoqueDTO estoqueDTO;

    @BeforeEach
    public void setUp() {
        Estoque estoque = new Estoque();
        estoqueDTO = new EstoqueDTO(estoque);
        estoqueDTO.setNomeEstoque("Estoque01");
        estoqueDTO.setAtivo(true);
    }

    @Test
    public void testFindById() throws Exception {
        Mockito.when(estoqueService.findById(1L)).thenReturn(estoqueDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/estoque/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testFindByNomeEstoque() throws Exception {
        Mockito.when(estoqueService.findByNomeEstoque("Estoque01")).thenReturn(estoqueDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/estoque/nome/Estoque01")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testFindByAtivo() throws Exception {
        List<EstoqueDTO> estoqueDTOList = Collections.singletonList(estoqueDTO);
        Mockito.when(estoqueService.findByAtivo(true)).thenReturn(estoqueDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/estoque/ativo/true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testFindAll() throws Exception {

        EstoqueDTO estoqueDTO1 = new EstoqueDTO(new Estoque());
        estoqueDTO1.setNomeEstoque("Estoque01");
        estoqueDTO1.setAtivo(true);

        EstoqueDTO estoqueDTO2 = new EstoqueDTO(new Estoque());
        estoqueDTO2.setNomeEstoque("Estoque02");
        estoqueDTO2.setAtivo(false);

        List<EstoqueDTO> estoqueDTOList = Arrays.asList(estoqueDTO1, estoqueDTO2);

        Mockito.when(estoqueService.findAll()).thenReturn(estoqueDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/estoque")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(estoqueDTOList)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testDeleteEstoque() throws Exception {

        EstoqueDTO estoqueDTO1 = new EstoqueDTO(new Estoque());
        estoqueDTO1.setNomeEstoque("Estoque01");
        estoqueDTO1.setAtivo(true);

        EstoqueDTO estoqueDTO2 = new EstoqueDTO(new Estoque());
        estoqueDTO2.setNomeEstoque("Estoque02");
        estoqueDTO2.setAtivo(false);

        List<EstoqueDTO> estoqueDTOList = Arrays.asList(estoqueDTO1, estoqueDTO2);

        Mockito.when(estoqueService.findAll()).thenReturn(estoqueDTOList);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/estoque/desativar/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
