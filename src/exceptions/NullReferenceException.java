package exceptions;

public class NullReferenceException extends MyException {
    public NullReferenceException() {
        super("Address not on the heap");
    }

    public NullReferenceException(String message) {
        super(message);
    }

    public NullReferenceException(int address) {
        super(String.format("Address '%s' not on the heap", address));
    }
}
