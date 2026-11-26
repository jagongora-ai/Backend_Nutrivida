package com.example.ms_agendar.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import com.example.ms_agendar.dto.ProfesionalExterno;
import com.example.ms_agendar.exception.RecursoNoEncontradoException;
import com.example.ms_agendar.exception.ServicioNoDisponibleException;

import reactor.core.publisher.Mono;

@Component 
public class ProfesionalClient {
 
    private final WebClient webClient;
 
    public ProfesionalClient(WebClient.Builder builder, @Value("${servicios.profesionales.url}") String url) {
        this.webClient = builder.clone().baseUrl(url).build();
    }
 
    public ProfesionalExterno obtener(Long id) {
        ProfesionalExterno profesional;
        try {
            profesional = webClient.get()
                    .uri("/api/profesionales/{id}", id)
                    .retrieve()
                    .onStatus(status -> status.value() == 404,
                            resp -> Mono.error(new RecursoNoEncontradoException("El profesional no existe")))
                    .bodyToMono(ProfesionalExterno.class)
                    .block();
        } catch (WebClientException e) {
            throw new ServicioNoDisponibleException("Servicio de profesionales no disponible");
        }
        if (profesional == null) {
            throw new RecursoNoEncontradoException("El profesional no existe");
        }
        return profesional;
    }
}

