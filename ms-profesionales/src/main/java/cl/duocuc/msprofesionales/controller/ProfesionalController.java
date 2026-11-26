package cl.duocuc.msprofesionales.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.duocuc.msprofesionales.dto.MensajeResponse;
import cl.duocuc.msprofesionales.dto.ProfesionalRequest;
import cl.duocuc.msprofesionales.dto.ProfesionalResponse;
import cl.duocuc.msprofesionales.service.ProfesionalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/profesionales")
@RequiredArgsConstructor
public class ProfesionalController {

    private final ProfesionalService service;

    @GetMapping
    public ResponseEntity<List<ProfesionalResponse>> listar(@RequestParam(required = false) String q) {
        return ResponseEntity.ok(service.listar(q));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfesionalResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<ProfesionalResponse> crear(@Valid @RequestBody ProfesionalRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<ProfesionalResponse> actualizar(@PathVariable Long id,
            @Valid @RequestBody ProfesionalRequest request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<MensajeResponse> eliminar(@PathVariable Long id) {
        return ResponseEntity.ok(service.eliminar(id));
    }
}
