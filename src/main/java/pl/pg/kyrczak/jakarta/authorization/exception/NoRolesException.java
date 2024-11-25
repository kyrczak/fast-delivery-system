package pl.pg.kyrczak.jakarta.authorization.exception;

public class NoRolesException extends SecurityException {

    public NoRolesException() {
    }

    public NoRolesException(String s) {
        super(s);
    }

    public NoRolesException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoRolesException(Throwable cause) {
        super(cause);
    }
}

