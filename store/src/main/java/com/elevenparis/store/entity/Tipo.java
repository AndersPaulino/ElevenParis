package com.elevenparis.store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_tipo",schema = "public")
public class Tipo extends AbstractEntity{
    @Getter @Setter
    @Column(name = "cl_tipo")
    private String nameTipo;
}
