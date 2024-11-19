package com.elevenparis.store.security;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class JWTConverter  implements Converter<Jwt, AbstractAuthenticationToken> {
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        // Extrair o campo "resource_access" que contém os papéis associados ao client
        Map<String, Map<String, Collection<String>>> resourceAccess = jwt.getClaim("resource_access");

        // Verificar se há papéis definidos para "eleven_backend"
        Collection<String> roles = Collections.emptyList();
        if (resourceAccess != null && resourceAccess.containsKey("eleven_backend")) {
            roles = resourceAccess.get("eleven_backend").get("roles");
        }

        // Mapear os papéis para SimpleGrantedAuthority
        var grants = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toList();

        return new JwtAuthenticationToken(jwt, grants);
    }



}