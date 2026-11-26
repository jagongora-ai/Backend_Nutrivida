package com.example.ms_agendar.security;

public record UsuarioAutenticado(Long id, String rol, String nombre, String correo, Long profesionalId) {
}
