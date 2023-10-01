package com.elevenparis.store.repository;

import com.elevenparis.store.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("SELECT e FROM Produto e WHERE e.nome = :nome")
    public Produto findByNomeProduto(@Param("nome") String nome);

    @Query("SELECT e FROM Produto e WHERE e.ativo = :ativo")
    public List<Produto> findByAtivo(@Param("ativo")boolean ativo);

}
