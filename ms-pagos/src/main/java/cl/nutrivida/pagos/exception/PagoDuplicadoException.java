package cl.nutrivida.pagos.exception;

public class PagoDuplicadoException extends RuntimeException {
    public PagoDuplicadoException() {
        super("La cita ya tiene un pago registrado.");
    }
}
