package com.elevenparis.store.dto;


import com.elevenparis.store.entity.UserRole;

public record RegisterDTO(String clientId,String username,String password,String grantType, UserRole role) {
}