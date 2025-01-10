package exceptions;

public class VariableExistsException extends MyException {
    public VariableExistsException() {
        super("A variable with this name already exists");
    }

    public VariableExistsException(String variableName) {
        super(String.format("A variable with the name '%s' already exists", variableName));
    }
}
