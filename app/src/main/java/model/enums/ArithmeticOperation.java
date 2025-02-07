package model.enums;

public enum ArithmeticOperation {
    ADDITION('+'),
    SUBTRACTION('-'),
    MULTIPLICATION('*'),
    DIVISION('/');

    public final char operation;

    private ArithmeticOperation(final char operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return String.valueOf(operation);
    }
}
