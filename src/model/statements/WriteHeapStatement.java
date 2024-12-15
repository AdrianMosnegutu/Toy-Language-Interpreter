package model.statements;

import java.util.Map;

import exceptions.IncompatibleTypesException;
import exceptions.MyException;
import exceptions.UndefinedVariableException;
import model.adt.IHeap;
import model.adt.ISymbolsTable;
import model.expressions.IExpression;
import model.states.ProgramState;
import model.types.IType;
import model.types.IntType;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

public class WriteHeapStatement implements IStatement {
    private final String variableName;
    private final IExpression expression;

    public WriteHeapStatement(String variableName, IExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        ISymbolsTable symbolsTable = state.getSymbolsTable();
        IHeap heap = state.getHeap();

        // Check if the variable is defined
        if (!symbolsTable.isVariableDefined(variableName)) {
            throw new UndefinedVariableException(variableName);
        }

        // Check if the variable is of type RefType
        IValue value = symbolsTable.getVariableValue(variableName);
        if (!value.getType().equals(new RefType())) {
            throw new IncompatibleTypesException(new RefType(), value.getType());
        }

        heap.setValueAt(((RefValue) value).getAddress(), expression.evaluate(symbolsTable, heap));
        return null;
    }

    @Override
    public Map<String, IType> typecheck(Map<String, IType> typeTable) throws MyException {
        IType typeExp = expression.typecheck(typeTable);

        if (typeExp.equals(new IntType())) {
            return typeTable;
        } else {
            throw new IncompatibleTypesException(new IntType(), typeExp);
        }
    }

    @Override
    public IStatement deepCopy() {
        return new WriteHeapStatement(variableName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("writeHeap(%s, %s);", variableName, expression);
    }
}
