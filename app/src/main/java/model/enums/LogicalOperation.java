package model.enums;

public enum LogicalOperation {
    AND("&&"),
    OR("||");

    public final String operation;

    private LogicalOperation(String operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return String.valueOf(operation);
    }
}
