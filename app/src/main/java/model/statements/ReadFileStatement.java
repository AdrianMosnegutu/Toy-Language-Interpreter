package model.statements;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

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

        if (!symbolsTable.isVariableDefined(variableName)) {
            throw new UndefinedVariableException(variableName);
        }

        IType variableType = symbolsTable.getVariableValue(variableName).getType();
        if (!variableType.equals(new IntType())) {
            throw new IncompatibleTypesException(new IntType(), variableType);
        }

        IValue expressionValue = expression.evaluate(symbolsTable, state.getHeap());
        BufferedReader fileDescriptor = fileTable.getFile((StringValue) expressionValue);
        if (fileDescriptor == null) {
            throw new UndefinedFileException(((StringValue) expressionValue).getValue());
        }

        String line;

        try {
            line = fileDescriptor.readLine();
        } catch (IOException e) {
            throw new FileException(e.getMessage());
        }

        int number = line != null ? Integer.parseInt(line) : 0;
        symbolsTable.setVariableValue(variableName, new IntValue(number));

        return null;
    }

    @Override
    public Map<String, IType> typecheck(Map<String, IType> typeTable) throws MyException {
        IType expressionType = expression.typecheck(typeTable);

        if (expressionType != null && !expressionType.equals(new StringType())) {
            throw new IncompatibleTypesException(new StringType(), expressionType);
        }

        return typeTable;
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
