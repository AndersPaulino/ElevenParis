package com.elevenparis.store.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.elevenparis.store.entity.User;
import lombok.Value;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.web.client.RestTemplate;

@Service
public class JwtServiceGenerator {

    public String generateToken(User userDetails) {


        //AQUI VOCÊ PODE COLOCAR O QUE MAIS VAI COMPOR O PAYLOAD DO TOKEN
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("username", userDetails.getUsername());
        extraClaims.put("id", userDetails.getId().toString());
        extraClaims.put("role", userDetails.getRole());


        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(new Date().getTime() + 3600000 * JwtConfig.HORAS_EXPIRACAO_TOKEN))
                .signWith(getSigningKey(), JwtConfig.ALGORITMO_ASSINATURA)
                .compact();
    }



    private String keycloakUrl = "http://192.168.43.87:8080/realms/myrealm";

    private String clientId = "app_elenparis";

    private String grantType = "password";

    private String username = "user_elevenparis";

    private String password = "user";

    public String getTokenFromKeycloak(User userDetails) {

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("username", userDetails.getUsername());
        extraClaims.put("id", userDetails.getId().toString());
        extraClaims.put("role", userDetails.getRole());

        RestTemplate restTemplate = new RestTemplate();

        String tokenUrl = keycloakUrl + "http://192.168.43.87:8080/realms/master/protocol/openid-connect/token";
        Map<String, String> params = new HashMap<>();
        params.put("realm", "your-realm"); // Substitua pelo seu realm

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        Map<String, String> body = new HashMap<>();
        body.put("client_id", clientId);
        body.put("grant_type", grantType);
        body.put("username", username);
        body.put("password", password);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                tokenUrl,
                HttpMethod.POST,
                entity,
                Map.class,
                params
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null) {
                return (String) responseBody.get("access_token");
            }
        }

        throw new RuntimeException("Failed to get token from Keycloak");
    }

    /*public String generateToken(User userDetails) {
        return getTokenFromKeycloak(userDetails);
    } */


    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JwtConfig.SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String extractUsername(String token) {
        return extractClaim(token,Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

}
