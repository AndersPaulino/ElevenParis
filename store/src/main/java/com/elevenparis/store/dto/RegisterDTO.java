package com.elevenparis.store.dto;


import com.elevenparis.store.entity.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}