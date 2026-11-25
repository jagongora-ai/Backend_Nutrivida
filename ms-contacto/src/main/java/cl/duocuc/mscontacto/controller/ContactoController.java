package cl.duocuc.mscontacto.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duocuc.mscontacto.dto.MensajeRequest;
import cl.duocuc.mscontacto.dto.MensajeResponse;
import cl.duocuc.mscontacto.dto.RespuestaRequest;
import cl.duocuc.mscontacto.service.MensajeService;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/mensajes")
@RequiredArgsConstructor
public class ContactoController {

    private final MensajeService service;

    @PostMapping()
    public ResponseEntity<MensajeResponse> enviar(@Valid @RequestBody MensajeRequest request,
            Authentication authentication) {

        Long idUsuario = authentication != null ? Long.valueOf(authentication.getName()) : null;
        return ResponseEntity.status(HttpStatus.CREATED).body(service.enviar(request, idUsuario));
    }

    @GetMapping("/mis/mensajes")
    @PreAuthorize("hasRole('Cliente')") // verifica antes de entrar si es cliente
    public ResponseEntity<List<MensajeResponse>> misMensajes(Authentication authentication) {
        Claims claims = (Claims) authentication.getDetails();
        String correo = claims.get("correo", String.class);
        return ResponseEntity.ok(service.misMensajes(correo));

    }

    @GetMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<List<MensajeResponse>> listar(@RequestParam(required = false) String estado) {
        return ResponseEntity.ok(service.listarMensajes(estado));
    }

    @PutMapping("/{id}/respuesta")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<MensajeResponse> responder(@PathVariable Long id,
            @Valid @RequestBody RespuestaRequest request) {
        return ResponseEntity.ok(service.responder(id, request));
    }

}
