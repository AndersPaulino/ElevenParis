package com.elevenparis.store.controller;


import com.elevenparis.store.dto.AuthenticationDTO;
import com.elevenparis.store.dto.RegisterDTO;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthenticationDTO data){
        HttpHeaders headers = new HttpHeaders();
        RestTemplate rt = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id",data.clientId());
        formData.add("username",data.username());
        formData.add("password",data.password());
        formData.add("grant_type",data.grantType());

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String,String>>(formData,headers);

        var result = rt.postForEntity("http://192.168.56.106:8080/realms/eleven/protocol/openid-connect/token",entity, String.class);

        return result;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO data) {
        // Configurações de cabeçalhos e autenticação
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Criação do JSON para o novo usuário
        Map<String, Object> userJson = new HashMap<>();
        userJson.put("username", data.username());
        userJson.put("enabled", true);
        userJson.put("email", data.email());
        userJson.put("attributes", Map.of("role", data.role()));

        Map<String, String> credentialsJson = new HashMap<>();
        credentialsJson.put("type", "password");
        credentialsJson.put("value", data.password());
        credentialsJson.put("temporary", "false");
        userJson.put("credentials", Collections.singletonList(credentialsJson));

        // Criação da entidade da requisição com o JSON do usuário
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(userJson, headers);
        RestTemplate restTemplate = new RestTemplate();

        // Enviar a solicitação POST para o Keycloak
        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://192.168.56.106:8080/admin/realms/eleven/users",
                entity,
                String.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok("User registered successfully");
        } else {
            return ResponseEntity.status(response.getStatusCode())
                    .body("User registration failed: " + response.getBody());
        }
    }


}