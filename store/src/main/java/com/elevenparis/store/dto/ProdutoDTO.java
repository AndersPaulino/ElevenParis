package com.elevenparis.store.dto;

import com.elevenparis.store.entity.Produto;
import com.elevenparis.store.entity.Tipo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ProdutoDTO {

    private Long id;
    private boolean ativo;
    private LocalDateTime registro;
    private LocalDateTime atualizar;
    private String nome;
    private String descricao;
    private String codigo;
    private Tipo tipo;

    public ProdutoDTO(Produto produto){
        id = produto.getId();
        ativo = produto.isAtivo();
        registro = produto.getRegistro();
        atualizar = produto.getAtualizar();
        nome = produto.getNome();
        descricao = produto.getDescricao();
        tipo = produto.getTipo();
    }

    public ProdutoDTO(Long id, boolean ativo, LocalDateTime registro, LocalDateTime atualizar, String nome, String descricao, Tipo tipo){
       this.id = id;
        this.ativo = ativo;
        this.registro = registro;
        this.atualizar = atualizar;
        this.nome = nome;
        this.descricao = descricao;
        this.tipo = tipo;
    }
}
