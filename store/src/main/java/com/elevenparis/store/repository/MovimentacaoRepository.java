package com.elevenparis.store.repository;

import com.elevenparis.store.entity.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao , Long> {
    @Query("SELECT e FROM Movimentacao e WHERE e.ativo = :ativo")
    List<Movimentacao> findByAtivo(@Param("ativo") boolean ativo);

    @Query("SELECT e FROM Movimentacao e WHERE DATE(e.registro) = :registro")
    List<Movimentacao> findByDiaRegistro(@Param("registro") LocalDate registro);

    @Query("SELECT e FROM Movimentacao e WHERE DATE(e.atualizar) = :atualizar")
    List<Movimentacao> findByDiaAtualizar(@Param("atualizar") LocalDate registro);


}
