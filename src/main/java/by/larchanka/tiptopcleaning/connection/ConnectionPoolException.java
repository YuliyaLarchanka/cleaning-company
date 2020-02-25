package by.larchanka.tiptopcleaning.connection;

public class ConnectionPoolException extends Throwable{
    public ConnectionPoolException() {
    }

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }

    ConnectionPoolException(Throwable cause) {
        super(cause);
    }
}
