package hr.javafx.sports.exceptions;

public class ValueZeroOrSmallerException extends RuntimeException{

    public ValueZeroOrSmallerException(String message) {
        super(message);
    }

    public ValueZeroOrSmallerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValueZeroOrSmallerException(Throwable cause) {
        super(cause);
    }
}
