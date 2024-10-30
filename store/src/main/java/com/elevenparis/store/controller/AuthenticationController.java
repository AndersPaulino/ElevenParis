package com.elevenparis.store.controller;


import com.elevenparis.store.dto.AuthenticationDTO;
import com.elevenparis.store.dto.RegisterDTO;
import jakarta.validation.Valid;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDTO data) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth("eyJhbGciOiJIUzUxMiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI2ZDJlYjg2Yy1iMjk0LTQzZjUtOWIwZC1kZmUxMTM3ZjYyMWIifQ.eyJleHAiOjAsImlhdCI6MTczMDI2MDg2NiwianRpIjoiZGU1NTY2NzUtNWYyNy00MDJhLThjNjktMmU4ZTM2ODliMDk0IiwiaXNzIjoiaHR0cDovLzE5Mi4xNjguNTYuMTA2OjgwODAvcmVhbG1zL2VsZXZlbiIsImF1ZCI6Imh0dHA6Ly8xOTIuMTY4LjU2LjEwNjo4MDgwL3JlYWxtcy9lbGV2ZW4iLCJ0eXAiOiJSZWdpc3RyYXRpb25BY2Nlc3NUb2tlbiIsInJlZ2lzdHJhdGlvbl9hdXRoIjoiYXV0aGVudGljYXRlZCJ9.IJr0vb3D2yBlXxTynRYMtNnTx9tTvZmDcIw2_aQQebL-CUpsG-GeF-6QjEvpa2QSlCQtlCJavdNRtQRz1qQemA"); // Substitua pelo token apropriado

        try {
            // Criar o objeto JSON para o novo usuário
            JSONObject userJson = new JSONObject();
            userJson.put("username", data.username());
            userJson.put("enabled", true);

            JSONObject credentialsJson = new JSONObject();
            credentialsJson.put("type", "password");
            credentialsJson.put("value", data.password());
            credentialsJson.put("temporary", false);
            userJson.put("credentials", new JSONArray().put(credentialsJson));

            // Criar a entidade com os dados JSON e os cabeçalhos
            HttpEntity<String> entity = new HttpEntity<>(userJson.toString(), headers);
            RestTemplate restTemplate = new RestTemplate();

            // Enviar a solicitação POST para criar o usuário no Keycloak
            ResponseEntity<String> response = restTemplate.postForEntity(
                    "http://192.168.56.106:8080/admin/realms/eleven/users",
                    entity,
                    String.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.ok("User registered successfully");
            } else {
                return ResponseEntity.status(response.getStatusCode()).body("Error: " + response.getBody());
            }

        } catch (org.json.JSONException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("JSON Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User registration failed: " + e.getMessage());
        }
    }


}