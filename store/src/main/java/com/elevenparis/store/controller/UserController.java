package com.elevenparis.store.controller;

import com.elevenparis.store.dto.MensagemDTO;
import com.elevenparis.store.entity.User;
import com.elevenparis.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<MensagemDTO> cadastrarNovoUsuario(@RequestBody User userRequest) {
        userService.cadastrarUsuario(userRequest);
        MensagemDTO mensagemDTO = new MensagemDTO("Usuário cadastrado com sucesso");
        return new ResponseEntity<>(mensagemDTO, HttpStatus.CREATED);
    }
}