package com.example.ms_agendar.security;

import java.io.IOException;
import java.util.List;
 
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
 
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter extends OncePerRequestFilter {
 
    private final JwtService jwtService;
 
    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }
 
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
 
        String encabezado = request.getHeader(HttpHeaders.AUTHORIZATION);
 
        if (encabezado != null && encabezado.startsWith("Bearer ")) {
            try {
                UsuarioAutenticado usuario = jwtService.validar(encabezado.substring(7));
                UsernamePasswordAuthenticationToken autenticacion = new UsernamePasswordAuthenticationToken(
                        usuario, null, List.of(new SimpleGrantedAuthority("ROLE_" + usuario.rol())));
                SecurityContextHolder.getContext().setAuthentication(autenticacion);
            } catch (JwtException | IllegalArgumentException e) {
                SecurityContextHolder.clearContext();
            }
        }
 
        chain.doFilter(request, response);
    }
}
