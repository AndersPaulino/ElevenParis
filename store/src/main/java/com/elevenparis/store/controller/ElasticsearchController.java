package com.elevenparis.store.controller;

import com.elevenparis.store.service.ElasticsearchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/elasticsearch")
public class ElasticsearchController {

    private final ElasticsearchService elasticsearchService;

    @Autowired
    public ElasticsearchController(ElasticsearchService elasticsearchService) {
        this.elasticsearchService = elasticsearchService;
    }

    @PostMapping("/index")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> indexDocument(@RequestBody Map<String, Object> jsonMap) {
        try {
            String index = "log";
            String json = new ObjectMapper().writeValueAsString(jsonMap);

            elasticsearchService.indexDocument(index, json);
            return ResponseEntity.ok("Documento indexado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao indexar o documento: " + e.getMessage());
        }
    }
}
