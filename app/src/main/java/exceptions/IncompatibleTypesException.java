package exceptions;

public class IncompatibleTypesException extends MyException {
    public IncompatibleTypesException() {
        super("Incompatible types");
    }

    public IncompatibleTypesException(String message) {
        super(message);
    }

    public IncompatibleTypesException(Object expected, Object actual) {
        super(String.format("Expected %s, got %s",
                expected.getClass().getName().substring(expected.getClass().getName().lastIndexOf('.') + 1),
                actual.getClass().getName().substring(actual.getClass().getName().lastIndexOf('.') + 1)));
    }
}
