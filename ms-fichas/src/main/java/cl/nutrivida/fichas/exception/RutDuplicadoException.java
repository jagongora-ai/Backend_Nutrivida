package cl.nutrivida.fichas.exception;

public class RutDuplicadoException extends RuntimeException {
    public RutDuplicadoException() {
        super("Ya existe una ficha con ese RUT.");
    }
}
