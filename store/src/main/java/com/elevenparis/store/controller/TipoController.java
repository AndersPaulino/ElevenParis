package com.elevenparis.store.controller;

import com.elevenparis.store.dto.TipoDTO;
import com.elevenparis.store.entity.Tipo;
import com.elevenparis.store.service.TipoService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipo")
@CrossOrigin(origins = "http://localhost:4200")
public class TipoController {

    private final TipoService tipoService;

    @Autowired
    public TipoController(TipoService tipoService){
        this.tipoService = tipoService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<TipoDTO> findById(@PathVariable Long id){
        return tipoService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping
    public ResponseEntity<List<TipoDTO>> findAll(){
        List<TipoDTO> tipoDTOS = tipoService.findAll();
        return ResponseEntity.ok(tipoDTOS);
    }
    @GetMapping("ativo/{ativo}")
    public ResponseEntity<List<TipoDTO>> findByAtivo(@PathVariable boolean ativo) {
        try {
            List<TipoDTO> tipoDTO = tipoService.findByAtivo(ativo);

            if (!tipoDTO.isEmpty()) {
                return ResponseEntity.ok(tipoDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping
    public ResponseEntity<String> cadastrar(@RequestBody Tipo tipo) {
        try {
            tipoService.cadastrar(tipo);
            return ResponseEntity.ok().body("Registro cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/nome/{id}")
    public ResponseEntity<String> atualizar(@PathVariable @NotNull Long id, @RequestBody Tipo tipo) {
        try {
            tipoService.atualizar(id, tipo);
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
            tipoService.deletar(id);
            return ResponseEntity.ok().body("Registro desativado com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao desativar o registro.");
        }
    }
}
