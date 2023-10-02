package com.elevenparis.store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_tipo",schema = "public")
public class Tipo extends AbstractEntity{
    @Getter @Setter
    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 50, message = "O nome deve ter no máximo {max} caracteres")
    @Column(name = "nome", nullable = false, length = 50)
    private String nome;
}
