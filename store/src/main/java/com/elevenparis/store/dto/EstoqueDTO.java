package com.elevenparis.store.dto;

import com.elevenparis.store.entity.Estoque;
import com.elevenparis.store.entity.Movimentacao;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class EstoqueDTO {

    private Long id;
    private boolean ativo;
    private LocalDateTime registro;
    private LocalDateTime atualizar;
    private String nomeEstoque;
    private List<Movimentacao> movimentacao;

    public EstoqueDTO(Estoque estoque){
        id = estoque.getId();
        ativo = estoque.isAtivo();
        registro = estoque.getRegistro();
        atualizar = estoque.getAtualizar();
        nomeEstoque = estoque.getNomeEstoque();
        movimentacao = estoque.getMovimentacao();
    }

    public EstoqueDTO(Long id, boolean ativo, LocalDateTime registro, LocalDateTime atualizar, String nomeEstoque, List<Movimentacao> movimentacao){
        this.id = id;
        this.ativo = ativo;
        this.registro = registro;
        this.atualizar = atualizar;
        this.nomeEstoque = nomeEstoque;
        this.movimentacao = movimentacao;
    }
}
