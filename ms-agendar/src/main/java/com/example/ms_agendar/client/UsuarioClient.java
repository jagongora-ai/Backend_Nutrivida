package com.example.ms_agendar.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import com.example.ms_agendar.dto.UsuarioExterno;
import com.example.ms_agendar.exception.RecursoNoEncontradoException;
import com.example.ms_agendar.exception.ServicioNoDisponibleException;

import reactor.core.publisher.Mono;

@Component 
public class UsuarioClient {
 
    private final WebClient webClient;
 
    public UsuarioClient(WebClient.Builder builder, @Value("${servicios.usuarios.url}") String url) {
        this.webClient = builder.clone().baseUrl(url).build();
    }
 
    public UsuarioExterno obtener(Long id) {
    UsuarioExterno usuario;
    try {
        usuario = webClient.get()
                .uri("/api/usuarios/{id}", id)
                .retrieve()
                .onStatus(status -> status.value() == 404,
                        resp -> Mono.error(new RecursoNoEncontradoException("Usuario no encontrado")))
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

