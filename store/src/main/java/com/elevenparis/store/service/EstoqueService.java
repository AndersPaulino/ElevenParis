package com.elevenparis.store.service;

import com.elevenparis.store.dto.EstoqueDTO;
import com.elevenparis.store.entity.Estoque;
import com.elevenparis.store.repository.EstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstoqueService {

    private EstoqueRepository estoqueRepository;

    @Autowired
    public EstoqueService(EstoqueRepository estoqueRepository){
        this.estoqueRepository = estoqueRepository;
    }

    public EstoqueDTO findById(Long id) {
        Estoque estoque = estoqueRepository.findById(id).get();
        EstoqueDTO estoqueDTO = new EstoqueDTO(estoque);
        return estoqueDTO;
    }

    public List<EstoqueDTO> findAll() {
        List<Estoque> estoques = estoqueRepository.findAll();
        return estoques.stream().map(EstoqueDTO::new).collect(Collectors.toList());
    }

    public EstoqueDTO findByNomeEstoque(String nomeEstoque) {
        Estoque estoque = estoqueRepository.findByNomeEstoque(nomeEstoque);
        EstoqueDTO estoqueDTO = new EstoqueDTO(estoque);
        if (estoqueDTO !=null) {
            return estoqueDTO;
        } else {
            try {
                throw new ChangeSetPersister.NotFoundException();
            } catch (ChangeSetPersister.NotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public List<EstoqueDTO> findByAtivo(boolean ativo) {
        List<Estoque> estoques = estoqueRepository.findByAtivo(ativo);

        List<EstoqueDTO> estoqueDTOs = new ArrayList<>();

        for (Estoque estoque : estoques) {
            EstoqueDTO dto = new EstoqueDTO(estoque);
            estoqueDTOs.add(dto);
        }
        return estoqueDTOs;
    }

    public List<EstoqueDTO> findByDiaRegistro(LocalDate registro) {
        List<Estoque> estoques = estoqueRepository.findByDiaRegistro(registro);

        List<EstoqueDTO> estoqueDTOS = new ArrayList<>();

        for (Estoque estoque : estoques) {
            EstoqueDTO dto = new EstoqueDTO(estoque);
            estoqueDTOS.add(dto);
        }
        return estoqueDTOS;
    }

    public List<EstoqueDTO> findByDiaAtualizar(LocalDate registro) {
        List<Estoque> estoques = estoqueRepository.findByDiaAtualizar(registro);

        List<EstoqueDTO> estoqueDTOS = new ArrayList<>();

        for (Estoque estoque : estoques) {
            EstoqueDTO dto = new EstoqueDTO(estoque);
            estoqueDTOS.add(dto);
        }
        return estoqueDTOS;
    }

}
