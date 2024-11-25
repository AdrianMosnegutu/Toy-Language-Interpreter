package model.expressions;

import exceptions.IncompatibleTypesException;
import exceptions.InvalidOperationException;
import exceptions.MyException;
import model.adt.IHeap;
import model.adt.ISymbolsTable;
import model.enums.ArithmeticRelation;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;

public class RelationalExpression implements IExpression {
    private final IExpression operand1;
    private final IExpression operand2;
    private final ArithmeticRelation operation;

    public RelationalExpression(IExpression operand1, IExpression operand2, ArithmeticRelation operation) {
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operation = operation;
    }

    @Override
    public IValue evaluate(ISymbolsTable symbolsTable, IHeap heap) throws MyException {
        // Check if the first operand evaluates to an IntValue
        IValue value1 = operand1.evaluate(symbolsTable, heap);
        if (!value1.getType().equals(new IntType())) {
            throw new IncompatibleTypesException(new IntType(), value1.getType());
        }

        // Check if the second operand evaluates to an IntValue
        IValue value2 = operand2.evaluate(symbolsTable, heap);
        if (!value2.getType().equals(new IntType())) {
            throw new IncompatibleTypesException(new IntType(), value2.getType());
        }

        Integer intValue1 = ((IntValue) value1).getValue();
        Integer intValue2 = ((IntValue) value2).getValue();

        switch (operation) {
            case EQUAL:
                return new BoolValue(intValue1 == intValue2);

            case NOT_EQUAL:
                return new BoolValue(intValue1 != intValue2);

            case LESS_THAN:
                return new BoolValue(intValue1 < intValue2);

            case LESS_THAN_OR_EQUAL:
                return new BoolValue(intValue1 <= intValue2);

            case GREATER_THAN:
                return new BoolValue(intValue1 > intValue2);

            case GREATER_THAN_OR_EQUAL:
                return new BoolValue(intValue1 >= intValue2);

            default:
                throw new InvalidOperationException();
        }
    }

    @Override
    public IExpression deepCopy() {
        return new RelationalExpression(operand1.deepCopy(), operand2.deepCopy(), operation);
    }

    @Override
    public String toString() {
        return operand1 + " " + operation + " " + operand2;
    }
}
