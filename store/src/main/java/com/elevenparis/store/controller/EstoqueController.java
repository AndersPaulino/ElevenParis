package com.elevenparis.store.controller;

import com.elevenparis.store.dto.EstoqueDTO;
import com.elevenparis.store.entity.Estoque;
import com.elevenparis.store.service.EstoqueService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/estoque")
@CrossOrigin(origins = "http://172.21.132.206:4200")
public class EstoqueController {

    private EstoqueService estoqueService;

    @Autowired
    public EstoqueController(EstoqueService estoqueService){
        this.estoqueService = estoqueService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstoqueDTO> findById(@PathVariable Long id) {
        return estoqueService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<EstoqueDTO>> findAll() {
        List<EstoqueDTO> estoqueDTO = estoqueService.findAll();
        return ResponseEntity.ok(estoqueDTO);
    }

    @GetMapping("/nome/{nomeEstoque}")
    public ResponseEntity<EstoqueDTO> findByNomeEstoque(@PathVariable String nomeEstoque){
        try {
            EstoqueDTO estoqueDTO = estoqueService.findByNomeEstoque(nomeEstoque);

            if (estoqueDTO != null) {
                return ResponseEntity.ok(estoqueDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("ativo/{ativo}")
    public ResponseEntity<List<EstoqueDTO>> findByAtivo(@PathVariable boolean ativo) {
        try {
            List<EstoqueDTO> estoqueDTO = estoqueService.findByAtivo(ativo);

            if (!estoqueDTO.isEmpty()) {
                return ResponseEntity.ok(estoqueDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("registro/dia/{registro}")
    public ResponseEntity<List<EstoqueDTO>> findByDiaRegistro(@PathVariable("registro") LocalDate registro) {
        try {
            List<EstoqueDTO> estoqueDTO = estoqueService.findByDiaRegistro(registro);

            if (!estoqueDTO.isEmpty()) {
                return ResponseEntity.ok(estoqueDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("atualizar/dia/{atualizar}")
    public ResponseEntity<List<EstoqueDTO>> findByDiaAtualizar(@PathVariable("atualizar") LocalDate atualizar) {
        try {
            List<EstoqueDTO> estoqueDTO = estoqueService.findByDiaAtualizar(atualizar);

            if (!estoqueDTO.isEmpty()) {
                return ResponseEntity.ok(estoqueDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<String> cadastrar(@RequestBody Estoque estoque) {
        try {
            estoqueService.cadastrar(estoque);
            return ResponseEntity.ok().body("Registro cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/nome/{id}")
    public ResponseEntity<String> atualizar(@PathVariable @NotNull Long id, @RequestBody Estoque estoque) {
        try {
            estoqueService.atualizar(id, estoque);
            return ResponseEntity.ok().body("Registro atualizado com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar o registro.");
        }
    }
    @DeleteMapping("/desativar/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        try {
            estoqueService.deletar(id);
            return ResponseEntity.ok().body("Registro desativado com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao desativar o registro.");
        }
    }

}
