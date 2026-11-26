package com.example.ms_agendar.exception;

public class ClaveInternaInvalidaException extends RuntimeException {
    public ClaveInternaInvalidaException(String mensaje) {
        super(mensaje);
    }
}