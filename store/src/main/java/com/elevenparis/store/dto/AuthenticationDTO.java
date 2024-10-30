package com.elevenparis.store.dto;

public record AuthenticationDTO(String password, String clientId ,String grantType, String username) {
}