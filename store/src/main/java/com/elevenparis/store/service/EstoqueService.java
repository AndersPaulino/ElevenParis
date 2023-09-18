package com.elevenparis.store.service;

import com.elevenparis.store.dto.EstoqueDTO;
import com.elevenparis.store.entity.Estoque;
import com.elevenparis.store.repository.EstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstoqueService {

    private EstoqueRepository estoqueRepository;

    @Autowired
    public EstoqueService(EstoqueRepository estoqueRepository){
        this.estoqueRepository = estoqueRepository;
    }

    public EstoqueDTO findBYId(Long id){
        Estoque entity = estoqueRepository.findById(id).orElse(null);
        if (entity == null){
            return null;
        }
        return new EstoqueDTO(entity);
    }
}
