package model.expressions;

import java.util.Map;

import exceptions.MyException;
import model.adt.IHeap;
import model.adt.ISymbolsTable;
import model.types.IType;
import model.values.IValue;
import model.values.RefValue;

public class ReadHeapExpression implements IExpression {
    private final IExpression expression;

    public ReadHeapExpression(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public IValue evaluate(ISymbolsTable symbolsTable, IHeap heap) throws MyException {
        return heap.getValueAt(((RefValue) expression.evaluate(symbolsTable, heap)).getAddress());
    }

    @Override
    public IType typecheck(Map<String, IType> typeTable) throws MyException {
        return null;
    }

    @Override
    public IExpression deepCopy() {
        return new ReadHeapExpression(expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("readHeap(%s)", expression);
    }
}
