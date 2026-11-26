package cl.nutrivida.fichas.exception;

public class FichaNoEncontradaException extends RuntimeException {
    public FichaNoEncontradaException() {
        super("La ficha no existe");
    }
}
