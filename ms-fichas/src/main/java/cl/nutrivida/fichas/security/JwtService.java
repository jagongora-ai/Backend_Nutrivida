package cl.nutrivida.fichas.security;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service

// en esta clase se crea el token, el token no es un string (hacemos que se
// convierta en uno) pero eso se hace en login ms usuario
// aqui solo va leer el token que se realiza al momento de inicar sesion
// dependiendo del rol jas inicia y ve que edpoints puede usar(accceso cosas asi xddd aprendi)

public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey obtenerClave() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public Claims leerToken(String token) {
        return Jwts.parser()
                .verifyWith(obtenerClave())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
