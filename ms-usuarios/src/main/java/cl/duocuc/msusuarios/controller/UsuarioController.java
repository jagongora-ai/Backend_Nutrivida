package cl.duocuc.msusuarios.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duocuc.msusuarios.dto.UsuarioResponse;
import cl.duocuc.msusuarios.service.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final AuthService service;

    @GetMapping("/me")
    public ResponseEntity<UsuarioResponse> miPerfil(Authentication authentication) {
        Long id = Long.valueOf(authentication.getName());
        return ResponseEntity.ok(service.miPerfil(id));
    }
}
