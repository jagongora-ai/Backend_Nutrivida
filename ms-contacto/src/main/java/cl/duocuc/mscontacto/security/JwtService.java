package cl.duocuc.mscontacto.security;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey obtenerClave() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // leer y validar el token (este micro no crea tokens, solo lee los de ms-usuarios)
    public Claims leerToken(String token) {
        return Jwts.parser()
            .verifyWith(obtenerClave())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
}
