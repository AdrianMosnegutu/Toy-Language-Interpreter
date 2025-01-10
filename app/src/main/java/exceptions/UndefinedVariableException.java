package exceptions;

public class UndefinedVariableException extends MyException {
    public UndefinedVariableException() {
        super("Variable is undefined");
    }

    public UndefinedVariableException(String variableName) {
        super("Variable '" + variableName + "' is undefined");
    }
}
