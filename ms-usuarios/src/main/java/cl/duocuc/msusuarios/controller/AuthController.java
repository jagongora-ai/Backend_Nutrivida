package cl.duocuc.msusuarios.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cl.duocuc.msusuarios.dto.LoginRequest;
import cl.duocuc.msusuarios.dto.LoginResponse;
import cl.duocuc.msusuarios.dto.RegistroRequest;
import cl.duocuc.msusuarios.dto.UsuarioResponse;
import cl.duocuc.msusuarios.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController 
@RequestMapping("/api/auth/")
@RequiredArgsConstructor 
public class AuthController {
    private final AuthService service;

    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponse> registrar (@Valid  @RequestBody RegistroRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(service.login(request));
    }
}
