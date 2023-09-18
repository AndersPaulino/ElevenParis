package com.elevenparis.store.entity;

public enum Tipo {


    //!!!!!!!!!!!!!!!!aRRUMAR AQUI CARAI
    ;
    private String nome;

    Tipo(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
