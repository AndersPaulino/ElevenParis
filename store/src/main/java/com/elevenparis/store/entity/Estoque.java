package com.elevenparis.store.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_estoque", schema = "public")
public class Estoque extends AbstractEntity{
    @Getter @Setter
    @Column(name = "cl_nome")
    private String nomeEstoque;

    @Getter @Setter
    @OneToMany
    @JoinColumn(name = "cl_estoque")
    private List<Movimentacao> movimentacao = new ArrayList<>();
}
