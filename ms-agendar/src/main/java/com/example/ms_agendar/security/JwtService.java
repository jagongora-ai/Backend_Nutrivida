package com.example.ms_agendar.security;

import java.nio.charset.StandardCharsets;
 
import javax.crypto.SecretKey;
 
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
 
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component 
public class JwtService {
 
    private final SecretKey clave;
 
    public JwtService(@Value("${jwt.secret}") String secreto) {
        this.clave = Keys.hmacShaKeyFor(secreto.getBytes(StandardCharsets.UTF_8));
    }
 
    public UsuarioAutenticado validar(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(clave)
                .build()
                .parseSignedClaims(token)
                .getPayload();
 
        String subject = claims.getSubject();
        String rol = claims.get("rol", String.class);
        if (subject == null || rol == null) {
            throw new JwtException("Token sin usuario o sin rol");
        }
 
        Number profesionalId = claims.get("profesionalId", Number.class);
        return new UsuarioAutenticado(
                Long.valueOf(subject),
                rol,
                claims.get("nombre", String.class),
                claims.get("correo", String.class),
                profesionalId == null ? null : profesionalId.longValue());
    }
}

