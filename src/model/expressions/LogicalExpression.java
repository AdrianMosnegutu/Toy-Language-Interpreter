package model.expressions;

import exceptions.IncompatibleTypesException;
import exceptions.InvalidOperationException;
import exceptions.MyException;
import model.adt.IHeap;
import model.adt.ISymbolsTable;
import model.enums.LogicalOperation;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.IValue;

public class LogicalExpression implements IExpression {
    private final IExpression operand1;
    private final IExpression operand2;
    private final LogicalOperation operation;

    public LogicalExpression(IExpression operand1, IExpression operand2, LogicalOperation operation) {
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operation = operation;
    }

    @Override
    public IValue evaluate(ISymbolsTable symbolsTable, IHeap heap) throws MyException {
        // Check if the first operand evaluates to a BoolValue
        IValue value1 = operand1.evaluate(symbolsTable, heap);
        if (!value1.getType().equals(new BoolType())) {
            throw new IncompatibleTypesException(new BoolType(), value1.getType());
        }

        // Check if the second operand evaluates to a BoolValue
        IValue value2 = operand2.evaluate(symbolsTable, heap);
        if (!value2.getType().equals(new BoolType())) {
            throw new IncompatibleTypesException(new BoolType(), value2.getType());
        }

        Boolean boolValue1 = ((BoolValue) value1).getValue();
        Boolean boolValue2 = ((BoolValue) value2).getValue();

        switch (operation) {
            case AND:
                return new BoolValue(boolValue1 && boolValue2);

            case OR:
                return new BoolValue(boolValue1 || boolValue2);

            default:
                throw new InvalidOperationException();
        }
    }

    @Override
    public IExpression deepCopy() {
        return new LogicalExpression(operand1.deepCopy(), operand2.deepCopy(), operation);
    }

    @Override
    public String toString() {
        return operand1 + " " + operation + " " + operand2;
    }
}
