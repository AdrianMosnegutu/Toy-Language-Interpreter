package model.expressions;

import java.util.Map;

import exceptions.MyException;
import model.adt.IHeap;
import model.adt.ISymbolsTable;
import model.types.IType;
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
    public IType typecheck(Map<String, IType> typeTable) throws MyException {
        return typeTable.get(name);
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
