package model.expressions;

import exceptions.MyException;
import model.adt.IHeap;
import model.adt.ISymbolsTable;
import model.values.IValue;

public interface IExpression {
    /**
     * Evaluates the expression using the provided symbols table and heap.
     *
     * @param symbolsTable the symbols table containing variable bindings
     * @param heap         the heap containing memory allocations
     * @return the result of the expression evaluation as an IValue
     * @throws MyException if an error occurs during evaluation
     */
    IValue evaluate(ISymbolsTable symbolsTable, IHeap heap) throws MyException;

    /**
     * Creates and returns a deep copy of this expression.
     *
     * @return a new IExpression object that is a deep copy of this expression
     */
    IExpression deepCopy();
}
