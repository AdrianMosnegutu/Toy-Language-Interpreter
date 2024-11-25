package model.expressions;

import exceptions.MyException;
import model.adt.IHeap;
import model.adt.ISymbolsTable;
import model.values.IValue;

public class VariableExpression implements IExpression {
    private final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    @Override
    public IValue evaluate(ISymbolsTable symbolsTable, IHeap heap) throws MyException {
        return symbolsTable.getVariableValue(name);
    }

    @Override
    public IExpression deepCopy() {
        return new VariableExpression(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
