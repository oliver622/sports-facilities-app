package hr.javafx.sports.exceptions;

public class EmptyNameException extends Exception{

    public EmptyNameException(String message) {
        super(message);
    }

    public EmptyNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyNameException(Throwable cause) {
        super(cause);
    }
}
