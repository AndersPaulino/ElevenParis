package com.elevenparis.store.service;

import com.elevenparis.store.dto.EstoqueDTO;
import com.elevenparis.store.entity.Estoque;
import com.elevenparis.store.repository.EstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EstoqueService {

    private EstoqueRepository estoqueRepository;

    @Autowired
    public EstoqueService(EstoqueRepository estoqueRepository){
        this.estoqueRepository = estoqueRepository;
    }

    @Transactional(readOnly = true)
    public EstoqueDTO findById(Long id) {
        Estoque estoque = estoqueRepository.findById(id).get();
        EstoqueDTO estoqueDTO = new EstoqueDTO(estoque);
        return estoqueDTO;
    }

    @Transactional(readOnly = true)
    public List<EstoqueDTO> findAll() {
        List<Estoque> estoques = estoqueRepository.findAll();
        return estoques.stream().map(EstoqueDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public List<EstoqueDTO> findByAtivo(boolean ativo) {
        List<Estoque> estoques = estoqueRepository.findByAtivo(ativo);

        List<EstoqueDTO> estoqueDTOs = new ArrayList<>();

        for (Estoque estoque : estoques) {
            EstoqueDTO dto = new EstoqueDTO(estoque);
            estoqueDTOs.add(dto);
        }
        return estoqueDTOs;
    }

    @Transactional(readOnly = true)
    public List<EstoqueDTO> findByDiaRegistro(LocalDate registro) {
        List<Estoque> estoques = estoqueRepository.findByDiaRegistro(registro);

        List<EstoqueDTO> estoqueDTOS = new ArrayList<>();

        for (Estoque estoque : estoques) {
            EstoqueDTO dto = new EstoqueDTO(estoque);
            estoqueDTOS.add(dto);
        }
        return estoqueDTOS;
    }

    @Transactional(readOnly = true)
    public List<EstoqueDTO> findByDiaAtualizar(LocalDate atualizar) {
        List<Estoque> estoques = estoqueRepository.findByDiaAtualizar(atualizar);

        List<EstoqueDTO> estoqueDTOS = new ArrayList<>();

        for (Estoque estoque : estoques) {
            EstoqueDTO dto = new EstoqueDTO(estoque);
            estoqueDTOS.add(dto);
        }
        return estoqueDTOS;
    }

    public void validarEstoque(final Estoque estoque) {
        if (estoque.getNomeEstoque() == null || estoque.getNomeEstoque().isEmpty()){
            throw new IllegalArgumentException("Nome do Estoque não informado");
        }
        if (!estoque.getNomeEstoque().matches("[a-zA-Z0-9 ]+")) {
            throw new IllegalArgumentException("Nome do Estoque inválido");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar( Estoque estoque) {
        validarEstoque(estoque);
        estoqueRepository.save(estoque);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void atualizar(Long id, Estoque estoque) {
        validarEstoque(estoque);

        Optional<Estoque> estoqueExistente = estoqueRepository.findById(id);

        if (estoqueExistente.isPresent()) {
            Estoque estoqueAtualizado = estoqueExistente.get();

            if (estoque.getNomeEstoque() != null) {
                estoqueAtualizado.setNomeEstoque(estoque.getNomeEstoque());
            }
            estoqueRepository.save(estoqueAtualizado);
        } else {
            throw new IllegalArgumentException("Id inválido!");
        }
    }

}
