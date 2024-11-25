package exceptions;

public class InvalidIndexException extends MyException {
    public InvalidIndexException() {
        super("Index out of bounds");
    }

    public InvalidIndexException(String message) {
        super(message);
    }
}
