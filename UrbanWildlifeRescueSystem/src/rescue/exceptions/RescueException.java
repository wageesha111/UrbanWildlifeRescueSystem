package rescue.exceptions;

public class RescueException extends Exception {
    public RescueException(String message) {
        super(message);
    }

    public RescueException(String message, Throwable cause) {
        super(message, cause);
    }
}
