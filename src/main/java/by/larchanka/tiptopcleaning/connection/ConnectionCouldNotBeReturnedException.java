package by.larchanka.tiptopcleaning.connection;

public class ConnectionCouldNotBeReturnedException extends RuntimeException {
    ConnectionCouldNotBeReturnedException() {
        super();
    }

    public ConnectionCouldNotBeReturnedException(String message) {
        super(message);
    }

    public ConnectionCouldNotBeReturnedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionCouldNotBeReturnedException(Throwable cause) {
        super(cause);
    }
}
