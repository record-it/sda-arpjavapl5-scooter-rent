package pl.sda.arpjavapl5.scooterrent.exception;

public class RollbackException extends RuntimeException{
    public RollbackException(String message) {
        super(message);
    }
}
