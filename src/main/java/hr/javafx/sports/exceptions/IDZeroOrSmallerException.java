package hr.javafx.sports.exceptions;

public class IDZeroOrSmallerException extends RuntimeException{
    public IDZeroOrSmallerException(String message) {
        super(message);
    }

    public IDZeroOrSmallerException(String message, Throwable cause) {
        super(message, cause);
    }

    public IDZeroOrSmallerException(Throwable cause) {
        super(cause);
    }
}
