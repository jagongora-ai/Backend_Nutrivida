package cl.duocuc.msusuarios.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cl.duocuc.msusuarios.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiracion}")
    private Long expiracion;

    private SecretKey obtenerClave() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // generador tokens
    public String generarToke(Usuario usuario){
        return Jwts.builder()
            .subject(String.valueOf(usuario.getId()))
            .claim("rol", usuario.getRol().getNombre())
            .claim("nombre", usuario.getNombre())
            .claim("correo", usuario.getCorreo())
            .claim("profesionalId", usuario.getProfesionalId())
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + expiracion))
            .signWith(obtenerClave())
            .compact();
    }
    
    //leer y validar el toke
    public Claims leerToken(String token){
        return  Jwts.parser()
            .verifyWith(obtenerClave())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
}
