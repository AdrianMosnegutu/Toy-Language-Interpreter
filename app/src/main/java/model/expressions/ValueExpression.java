package model.expressions;

import java.util.Map;

import exceptions.MyException;
import model.adt.IHeap;
import model.adt.ISymbolsTable;
import model.types.IType;
import model.values.IValue;

public class ValueExpression implements IExpression {
    private final IValue value;

    public ValueExpression(IValue value) {
        this.value = value;
    }

    @Override
    public IValue evaluate(ISymbolsTable symbolsTable, IHeap heap) throws MyException {
        return value;
    }

    @Override
    public IType typecheck(Map<String, IType> typeTable) throws MyException {
        return value.getType();
    }

    @Override
    public IExpression deepCopy() {
        return new ValueExpression(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
