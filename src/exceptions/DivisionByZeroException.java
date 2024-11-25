package exceptions;

public class DivisionByZeroException extends MyException {
    public DivisionByZeroException() {
        super("Cannot divide by zero");
    }

    public DivisionByZeroException(String message) {
        super(message);
    }
}
