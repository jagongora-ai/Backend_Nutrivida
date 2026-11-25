package cl.nutrivida.pagos.exception;

public class BoletaNoEncontradaException extends RuntimeException {
    public BoletaNoEncontradaException() {
        super("No se encontró una boleta para esa cita.");
    }
}
