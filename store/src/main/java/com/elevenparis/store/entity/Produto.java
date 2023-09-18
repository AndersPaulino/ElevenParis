package com.elevenparis.store.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_produto" , schema = "public")
public class Produto extends AbstractEntity{
    @Getter @Setter
    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 50, message = "O nome deve ter no máximo {max} caracteres")
    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "estoque_id")
    private Estoque estoque;

    @Getter @Setter
    private Tipo tipo;

    @Getter @Setter
    @NotBlank(message = "O codigo é obrigatório")
    @Size(max = 255, message = "O codigo deve ter no máximo {max} caracteres")
    @Column(name = "codigo", nullable = false, length = 255)
    private String codigo;

    @Getter @Setter
    @NotBlank(message = "A descrição é obrigatório")
    @Size(max = 255, message = "A descrição deve ter no máximo {max} caracteres")
    @Column(name = "descricao", nullable = false, length = 255)
    private String descricao;

    public Produto() {
    }

    public Produto(String nome, Estoque estoque, Tipo tipo, String codigo, String descricao) {
        this.nome = nome;
        this.estoque = estoque;
        this.tipo = tipo;
        this.codigo = codigo;
        this.descricao = descricao;
    }

}
