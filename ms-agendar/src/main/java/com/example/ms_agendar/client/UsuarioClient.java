package com.example.ms_agendar.client;

import org.apache.hc.core5.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import com.example.ms_agendar.dto.UsuarioExterno;
import com.example.ms_agendar.exception.AccesoDenegadoException;
import com.example.ms_agendar.exception.RecursoNoEncontradoException;
import com.example.ms_agendar.exception.ServicioNoDisponibleException;

import reactor.core.publisher.Mono;

@Component 
public class UsuarioClient {
 
    private final WebClient webClient;
 
    public UsuarioClient(WebClient.Builder builder, @Value("${servicios.usuarios.url}") String url) {
        this.webClient = builder.clone().baseUrl(url).build();
    }
 
    public UsuarioExterno miPerfil(String authorization) {
        UsuarioExterno usuario;
        try {
            usuario = webClient.get()
                    .uri("/api/usuarios/me")
                    .header(HttpHeaders.AUTHORIZATION, authorization)
                    .retrieve()
                    .onStatus(status -> status.value() == 404,
                            resp -> Mono.error(new RecursoNoEncontradoException("Usuario no encontrado")))
                    .onStatus(status -> status.value() == 401 || status.value() == 403,
                            resp -> Mono.error(new AccesoDenegadoException("No tienes permiso para ver este usuario")))
                    .bodyToMono(UsuarioExterno.class)
                    .block();
        } catch (WebClientException e) {
            throw new ServicioNoDisponibleException("Servicio de usuarios no disponible");
        }
        if (usuario == null) {
            throw new RecursoNoEncontradoException("Usuario no encontrado");
        }
        return usuario;
    }
}

