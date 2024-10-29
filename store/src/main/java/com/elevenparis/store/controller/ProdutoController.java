package com.elevenparis.store.controller;


import com.elevenparis.store.dto.ProdutoDTO;
import com.elevenparis.store.entity.Produto;
import com.elevenparis.store.service.ProdutoService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController {

    private final ProdutoService produtoService;

    @Autowired
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProdutoDTO> findById(@PathVariable Long id) {
        return produtoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProdutoDTO>> findAll() {
        List<ProdutoDTO> produtoDTO = produtoService.findAll();
        return ResponseEntity.ok(produtoDTO);
    }

    @GetMapping("/nome/{nome}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProdutoDTO> findByNomeProduto(@PathVariable String nome) {
        try {
            ProdutoDTO produtoDTO = produtoService.findByNome(nome);

            if (produtoDTO != null) {
                return ResponseEntity.ok(produtoDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("ativo/{ativo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProdutoDTO>> findByAtivo(@PathVariable boolean ativo) {
        try {
            List<ProdutoDTO> produtoDTO = produtoService.findByAtivo(ativo);

            if (!produtoDTO.isEmpty()) {
                return ResponseEntity.ok(produtoDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("registro/dia/{registro}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProdutoDTO>> findByDiaRegistro(@PathVariable("registro") LocalDate registro) {
        try {
            List<ProdutoDTO> produtoDTO = produtoService.findByDiaRegistro(registro);

            if (!produtoDTO.isEmpty()) {
                return ResponseEntity.ok(produtoDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("atualizar/dia/{atualizar}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProdutoDTO>> findByDiaAtualizar(@PathVariable("atualizar") LocalDate atualizar) {
        try {
            List<ProdutoDTO> produtoDTO = produtoService.findByDiaAtualizar(atualizar);

            if (!produtoDTO.isEmpty()) {
                return ResponseEntity.ok(produtoDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> cadastrar(@RequestBody Produto produto) {
        try {
            produtoService.cadastrar(produto);
            return ResponseEntity.ok().body("Registro cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/nome/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> atualizar(@PathVariable @NotNull Long id, @RequestBody Produto produto) {
        try {
            produtoService.atualizar(id, produto);
            return ResponseEntity.ok().body("Registro atualizado com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar o registro.");
        }
    }

    @DeleteMapping("/desativar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        try {
            produtoService.deletar(id);
            return ResponseEntity.ok().body("Registro desativado com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao desativar o registro");
        }
    }
}
