package com.elevenparis.store.controller;

import com.elevenparis.store.dto.EstoqueDTO;
import com.elevenparis.store.entity.Estoque;
import com.elevenparis.store.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/api/estoque")
public class EstoqueController {

    private EstoqueService estoqueService;

    @Autowired
    public EstoqueController(EstoqueService estoqueService){
        this.estoqueService = estoqueService;
    }


}
