package com.pragma.hogar360.servicesvisits.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    public Long extractUserIdFromToken(String token) {
        log.info("JwtService (home) - Token recibido para extraer userId: {}", token);
        String subject = extractClaim(token, Claims::getSubject);
        try {
            Long userId = Long.parseLong(subject);
            log.debug("JwtService (home) - User ID extraído del subject: {}", userId);
            return userId;
        } catch (NumberFormatException e) {
            log.error("JwtService (home) - Formato inválido de userId en el subject del token: {}", subject);
            return null; // O podrías lanzar una excepción personalizada
        }
    }
    public String extractEmailFromToken(String token) {
        log.info("JwtService (visits) - Token recibido para extraer email: {}", token);
        try {
            String email = extractClaim(token, claims -> claims.get("email", String.class));
            log.debug("JwtService (visits) - Email extraído del token: {}", email);
            return email;
        } catch (Exception e) {
            log.error("JwtService (visits) - Error al extraer el email del token: {}", e.getMessage());
            return null;
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        log.debug("JwtService (home) - Extrayendo claim del token: {}", token);
        final Claims claims = extractAllClaims(token);
        log.trace("JwtService (home) - Claims extraídas: {}", claims);
        return claimsResolver.apply(claims);
    }

    public List<SimpleGrantedAuthority> extractRoles(String token) {
        log.info("JwtService (home) - Extrayendo roles del token: {}", token);
        List<String> rolesFromToken = (List<String>) extractClaim(token, claims -> claims.get("roles"));

        log.debug("JwtService (home) - Roles extraídas del token (raw): {}", rolesFromToken);

        List<SimpleGrantedAuthority> authorities = null;
        if (rolesFromToken != null) {
            authorities = rolesFromToken.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            log.debug("JwtService (home) - Authorities creadas: {}", authorities);
        } else {
            log.warn("JwtService (home) - No se encontraron roles en el token.");
        }
        return authorities;
    }

    public boolean isTokenValid(String token) {
        return !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new java.util.Date());
    }

    private java.util.Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        log.debug("JwtService (home) - Parsing token: {}", token);
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}