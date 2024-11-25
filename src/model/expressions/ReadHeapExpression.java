package model.expressions;

import exceptions.IncompatibleTypesException;
import exceptions.MyException;
import model.adt.IHeap;
import model.adt.ISymbolsTable;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

public class ReadHeapExpression implements IExpression {
    private final IExpression expression;

    public ReadHeapExpression(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public IValue evaluate(ISymbolsTable symbolsTable, IHeap heap) throws MyException {
        // Check if the expression evaluates to a RefValue
        IValue value = expression.evaluate(symbolsTable, heap);
        if (!value.getType().equals(new RefType())) {
            throw new IncompatibleTypesException(new RefType(), value.getType());
        }

        return heap.getValueAt(((RefValue) value).getAddress());
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
