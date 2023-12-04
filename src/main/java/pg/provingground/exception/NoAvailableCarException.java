package pg.provingground.exception;

public class NoAvailableCarException extends RuntimeException{
    public NoAvailableCarException() {
        super();
    }

    public NoAvailableCarException(String message) {
        super(message);
    }

    public NoAvailableCarException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoAvailableCarException(Throwable cause) {
        super(cause);
    }

}
