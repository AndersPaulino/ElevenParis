package com.elevenparis.store.repository;

import com.elevenparis.store.entity.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    @Query("SELECT e FROM Estoque e WHERE e.nomeEstoque = :nomeEstoque")
    public Estoque findByNomeEstoque(@Param("nomeEstoque") String nomeEstoque);

    @Query("SELECT e FROM Estoque e WHERE e.ativo = :ativo")
    public List<Estoque> findByAtivo(@Param("ativo") boolean ativo);

    @Query("SELECT e FROM Estoque e WHERE DATE(e.registro) = :registro")
    List<Estoque> findByDiaRegistro(@Param("registro") LocalDate registro);

    @Query("SELECT e FROM Estoque e WHERE DATE(e.atualizar) = :atualizar")
    List<Estoque> findByDiaAtualizar(@Param("atualizar") LocalDate registro);

}
