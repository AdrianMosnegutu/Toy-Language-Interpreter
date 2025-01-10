package exceptions;

public class EmptyStackException extends MyException {
    public EmptyStackException() {
        super("Cannot pop from an empty stack");
    }

    public EmptyStackException(String message) {
        super(message);
    }
}
