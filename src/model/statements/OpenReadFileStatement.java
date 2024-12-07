package model.statements;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import exceptions.FileAlreadyOpenedException;
import exceptions.FileException;
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

public class OpenReadFileStatement implements IStatement {
    private final IExpression expression;

    public OpenReadFileStatement(IExpression expression) {
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

        // Check if the file is already open
        StringValue fileName = (StringValue) value;
        if (fileTable.isOpen(fileName)) {
            throw new FileAlreadyOpenedException(fileName);
        }

        // Try to open the file
        try {
            fileTable.setFile(fileName, new BufferedReader(new FileReader(fileName.getValue())));
        } catch (FileNotFoundException e) {
            throw new UndefinedFileException(fileName.getValue());
        } catch (Exception e) {
            throw new FileException(e.getMessage());
        }

        return null;
    }

    public IStatement deepCopy() {
        return new OpenReadFileStatement(expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("openReadFile(%s);", expression);
    }
}
