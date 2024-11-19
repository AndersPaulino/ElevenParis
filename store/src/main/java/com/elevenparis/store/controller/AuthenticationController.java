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

        var result = rt.postForEntity("http://192.168.56.7:8080/realms/PROJETO_MENSAL/protocol/openid-connect/token",entity, String.class);

        return result;
    }

}