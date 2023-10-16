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
    @JoinColumn(name = "tipo_id")
    private Tipo tipo;

    @Getter @Setter
    @NotBlank(message = "A descrição é obrigatório")
    @Size(max = 255, message = "A descrição deve ter no máximo {max} caracteres")
    @Column(name = "descricao", nullable = false, length = 255)
    private String descricao;

}
