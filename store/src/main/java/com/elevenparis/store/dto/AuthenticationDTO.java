package com.elevenparis.store.dto;

public record AuthenticationDTO(String clientId,String username,String password,String grantType) {
}