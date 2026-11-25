package cl.nutrivida.pagos.exception;

public class AgendaException extends RuntimeException {
    private final int status;

    public AgendaException(int status, String message) {
        super(message);
        this.status = status;
    }

    public int getStatus() { return status; }
}
