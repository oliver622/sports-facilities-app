package hr.javafx.sports.exceptions;

public class NoFacilityWithIDException extends Exception{

    public NoFacilityWithIDException(String message) {
        super(message);
    }

    public NoFacilityWithIDException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoFacilityWithIDException(Throwable cause) {
        super(cause);
    }
}
