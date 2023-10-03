package com.elevenparis.store.repository;

import com.elevenparis.store.entity.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoRepository extends JpaRepository<Tipo, Long> {
    @Query("SELECT e FROM Tipo e WHERE e.ativo = :ativo")
    public List<Tipo> findByAtivo(@Param("ativo")boolean ativo);

}
