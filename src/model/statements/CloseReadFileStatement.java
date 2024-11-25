package model.statements;

import exceptions.IncompatibleTypesException;
import exceptions.MyException;
import exceptions.UndefinedFileException;
import model.adt.IFileTable;
import model.adt.ISymbolsTable;
import model.expressions.IExpression;
import model.states.ProgramState;
import model.types.StringType;
import model.values.IValue;
import model.values.StringValue;

public class CloseReadFileStatement implements IStatement {
    private final IExpression expression;

    public CloseReadFileStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        ISymbolsTable symbolsTable = state.getSymbolsTable();
        IFileTable fileTable = state.getFileTable();

        // Check if the expression evaluates to a StringValue
        IValue value = expression.evaluate(symbolsTable, state.getHeap());
        if (!value.getType().equals(new StringType())) {
            throw new IncompatibleTypesException(new StringType(), value.getType());
        }

        // Check if the file is open
        StringValue fileName = (StringValue) value;
        if (fileTable.getFile(fileName) == null) {
            throw new UndefinedFileException(fileName.getValue());
        }

        fileTable.closeFile(fileName);
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new CloseReadFileStatement(expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("closeReadFile(%s);", expression);
    }
}
