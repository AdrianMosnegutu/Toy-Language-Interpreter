package model.statements;

import java.io.BufferedReader;
import java.io.IOException;

import exceptions.FileException;
import exceptions.IncompatibleTypesException;
import exceptions.MyException;
import exceptions.UndefinedFileException;
import exceptions.UndefinedVariableException;
import model.adt.IFileTable;
import model.adt.ISymbolsTable;
import model.expressions.IExpression;
import model.states.ProgramState;
import model.types.IType;
import model.types.IntType;
import model.types.StringType;
import model.values.IValue;
import model.values.IntValue;
import model.values.StringValue;

public class ReadFileStatement implements IStatement {
    private final IExpression expression;
    private final String variableName;

    public ReadFileStatement(IExpression expression, String variableName) {
        this.expression = expression;
        this.variableName = variableName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        ISymbolsTable symbolsTable = state.getSymbolsTable();
        IFileTable fileTable = state.getFileTable();

        // Check if the variable is defined
        if (!symbolsTable.isVariableDefined(variableName)) {
            throw new UndefinedVariableException(variableName);
        }

        // Check if the variable is of type IntType
        IType variableType = symbolsTable.getVariableValue(variableName).getType();
        if (!variableType.equals(new IntType())) {
            throw new IncompatibleTypesException(new IntType(), variableType);
        }

        // Check if the expression evaluates to a StringValue
        IValue value = expression.evaluate(symbolsTable, state.getHeap());
        if (!value.getType().equals(new StringType())) {
            throw new IncompatibleTypesException(new StringType(), value.getType());
        }

        // Check if the file is open
        BufferedReader fileDescriptor = fileTable.getFile((StringValue) value);
        if (fileDescriptor == null) {
            throw new UndefinedFileException(((StringValue) value).getValue());
        }

        // Try to read a line from the file
        try {
            String line = fileDescriptor.readLine();
            int number = line != null ? Integer.parseInt(line) : 0;
            symbolsTable.setVariableValue(variableName, new IntValue(number));
        } catch (IOException e) {
            throw new FileException(e.getMessage());
        }

        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new ReadFileStatement(expression.deepCopy(), variableName);
    }

    @Override
    public String toString() {
        return String.format("readFile(%s, %s);", expression, variableName);
    }
}