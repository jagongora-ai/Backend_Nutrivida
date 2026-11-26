package com.example.ms_agendar.security;

public record UsuarioAutenticado(Long id, String rol, String nombre, String rut, String correo, Long profesionalId) {
}
