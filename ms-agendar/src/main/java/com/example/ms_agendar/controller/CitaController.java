package com.example.ms_agendar.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;	
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.ms_agendar.dto.CitaResponse;
import com.example.ms_agendar.dto.CitaRequest;
import com.example.ms_agendar.dto.HorasOcupadasResponse;
import com.example.ms_agendar.dto.MensajeResponse;
import com.example.ms_agendar.security.UsuarioAutenticado;
import com.example.ms_agendar.service.CitaService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController 
@RequestMapping("/api/citas")
@RequiredArgsConstructor 
public class CitaController {

    private final CitaService service;

    @GetMapping("/ocupadas")
    public ResponseEntity<HorasOcupadasResponse> ocupadas(@RequestParam Long doctorId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(service.horasOcupadas(doctorId, fecha));
    }

    @PostMapping
public ResponseEntity<CitaResponse> crear(
        @Valid @RequestBody CitaRequest request,
        @Parameter(hidden = true) @AuthenticationPrincipal UsuarioAutenticado usuario) {
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(service.crear(request, usuario.id()));
}

    @GetMapping("/mias")
    public ResponseEntity<List<CitaResponse>> mias( @Parameter(hidden = true) @AuthenticationPrincipal UsuarioAutenticado usuario) {
        return ResponseEntity.ok(service.misCitas(usuario.id()));
    }

    @GetMapping("/agenda")
    public ResponseEntity<List<CitaResponse>> agenda(@Parameter(hidden = true) @AuthenticationPrincipal UsuarioAutenticado usuario) {
        return ResponseEntity.ok(service.agendaProfesional(usuario.profesionalId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaResponse> obtener(
            @PathVariable Long id,
            @Parameter(hidden = true) @AuthenticationPrincipal UsuarioAutenticado usuario) {
        return ResponseEntity.ok(service.obtener(id, usuario.id()));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<MensajeResponse> cancelar(
            @PathVariable Long id,
            @Parameter(hidden = true) @AuthenticationPrincipal UsuarioAutenticado usuario) {
        return ResponseEntity.ok(service.cancelar(id, usuario.id()));
    }

    @PatchMapping("/{id}/pagada")
    public ResponseEntity<CitaResponse> marcarPagada(
            @PathVariable Long id,
            @RequestHeader(value = "X-ClaveInterna", required = false) String clave) {
        return ResponseEntity.ok(service.marcarPagada(id, clave));
    }
}

