package pg.provingground.exception;

public class NoAvailableGroundException extends RuntimeException{
    public NoAvailableGroundException() {
        super();
    }

    public NoAvailableGroundException(String message) {
        super(message);
    }

    public NoAvailableGroundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoAvailableGroundException(Throwable cause) {
        super(cause);
    }

}
