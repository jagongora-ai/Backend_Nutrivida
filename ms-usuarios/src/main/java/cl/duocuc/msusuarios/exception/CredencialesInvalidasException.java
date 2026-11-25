package cl.duocuc.msusuarios.exception;

public class CredencialesInvalidasException extends RuntimeException{
    public CredencialesInvalidasException(String  mensaje){
        super(mensaje);
    }
}
