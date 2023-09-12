package com.elevenparis.store.dto;

import com.elevenparis.store.entity.Estoque;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class EstoqueDTO {

    private Long id;
    private boolean ativo;
    private LocalDateTime registro;
    private LocalDateTime atualizar;
    private String nomeEstoque;

    public EstoqueDTO(Estoque estoque){
        id = estoque.getId();
        ativo = estoque.isAtivo();
        registro = estoque.getRegistro();
        atualizar = estoque.getAtualizar();
        nomeEstoque = estoque.getNomeEstoque();
    }
}
