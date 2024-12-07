package model.statements;

import exceptions.IncompatibleTypesException;
import exceptions.MyException;
import exceptions.UndefinedVariableException;
import model.adt.IHeap;
import model.adt.ISymbolsTable;
import model.expressions.IExpression;
import model.states.ProgramState;
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

        // Check if the expression evaluates to a value of the same type as the location
        // type inside the RefValue
        IValue expressionValue = expression.evaluate(symbolsTable, heap);
        if (!expressionValue.getType().equals(((RefValue) value).getLocationType())) {
            throw new IncompatibleTypesException(((RefValue) value).getLocationType(), expressionValue.getType());
        }

        heap.setValueAt(((RefValue) value).getAddress(), expressionValue);
        return null;
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
