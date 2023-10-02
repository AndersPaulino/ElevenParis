package com.elevenparis.store.dto;

import com.elevenparis.store.entity.Movimentacao;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class MovimentacaoDTO {
    private Long id;
    private boolean ativo;
    private LocalDateTime registro;
    private LocalDateTime atualizar;
    private int entrada;
    private int saida;


    public MovimentacaoDTO(Movimentacao movimentacao){
        id = movimentacao.getId();
        ativo = movimentacao.isAtivo();
        registro = movimentacao.getRegistro();
        atualizar = movimentacao.getAtualizar();
        entrada = movimentacao.getEntrada();
        saida = movimentacao.getSaida();
    }

    public MovimentacaoDTO(Long id, boolean ativo, LocalDateTime registro, LocalDateTime atualizar, int entrada, int saida) {
        this.id = id;
        this.ativo = ativo;
        this.registro = registro;
        this.atualizar = atualizar;
        this.entrada = entrada;
        this.saida = saida;
    }
}
