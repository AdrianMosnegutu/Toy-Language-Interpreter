package model.statements;

import java.util.Map;

import exceptions.IncompatibleTypesException;
import exceptions.MyException;
import exceptions.UndefinedFileException;
import model.adt.IFileTable;
import model.adt.ISymbolsTable;
import model.expressions.IExpression;
import model.states.ProgramState;
import model.types.IType;
import model.types.StringType;
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

        StringValue fileName = (StringValue) expression.evaluate(symbolsTable, state.getHeap());
        if (fileTable.getFile(fileName) == null) {
            throw new UndefinedFileException(fileName.getValue());
        }

        fileTable.closeFile(fileName);
        return null;
    }

    @Override
    public Map<String, IType> typecheck(Map<String, IType> typeTable) throws MyException {
        IType typeExp = expression.typecheck(typeTable);

        if (!typeExp.equals(new StringType())) {
            throw new IncompatibleTypesException(new StringType(), typeExp);
        }

        return typeTable;
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
