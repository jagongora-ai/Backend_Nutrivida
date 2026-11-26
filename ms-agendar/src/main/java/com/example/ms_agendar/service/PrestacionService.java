package com.example.ms_agendar.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_agendar.dto.PrestacionResponse;
import com.example.ms_agendar.model.Prestacion;
import com.example.ms_agendar.repository.PrestacionRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class PrestacionService {
 
    private final PrestacionRepository prestacionRepository;
 
    public List<PrestacionResponse> listar() {
        return prestacionRepository.findAll().stream().map(this::aResponse).toList();
    }
 
    private PrestacionResponse aResponse(Prestacion p) {
        return new PrestacionResponse(
                p.getId(),
                p.getValor(),
                p.getEtiqueta(),
                p.getPrecio(),
                p.getDuracion()
        );
    }
}