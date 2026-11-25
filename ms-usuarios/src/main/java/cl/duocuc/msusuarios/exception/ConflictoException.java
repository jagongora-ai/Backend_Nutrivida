package cl.duocuc.msusuarios.exception;

public class ConflictoException extends RuntimeException{
    public ConflictoException(String mensaje){
        super(mensaje);
    }
}
