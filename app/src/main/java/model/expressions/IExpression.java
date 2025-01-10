package model.expressions;

import java.util.Map;

import exceptions.MyException;
import model.adt.IHeap;
import model.adt.ISymbolsTable;
import model.types.IType;
import model.values.IValue;

public interface IExpression {
    /**
     * Evaluates the expression using the provided symbols table and heap.
     *
     * @param symbolsTable the symbols table containing variable bindings
     * @param heap         the heap containing memory allocations
     * @return the result of the expression evaluation as an IValue
     * 
     * @throws MyException if an error occurs during evaluation
     */
    IValue evaluate(ISymbolsTable symbolsTable, IHeap heap) throws MyException;

    /**
     * Checks the type of the expression based on the provided symbols table.
     *
     * @param symbolsTable a map containing the variable names and their
     *                     corresponding types
     * @return the type of the expression
     *
     * @throws MyException if there is a type mismatch or any other type-related
     *                     error
     */
    IType typecheck(Map<String, IType> symbolsTable) throws MyException;

    /**
     * Creates and returns a deep copy of this expression.
     *
     * @return a new IExpression object that is a deep copy of this expression
     */
    IExpression deepCopy();
}
