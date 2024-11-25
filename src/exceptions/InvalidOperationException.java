package exceptions;

public class InvalidOperationException extends MyException {
    public InvalidOperationException() {
        super("Invalid operation");
    }

    public InvalidOperationException(String message) {
        super(message);
    }
}
