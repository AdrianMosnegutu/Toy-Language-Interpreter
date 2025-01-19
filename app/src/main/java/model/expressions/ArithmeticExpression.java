package model.expressions;

import java.util.Map;

import exceptions.DivisionByZeroException;
import exceptions.IncompatibleTypesException;
import exceptions.InvalidOperationException;
import exceptions.MyException;
import model.adt.IHeap;
import model.adt.ISymbolsTable;
import model.enums.ArithmeticOperation;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

public class ArithmeticExpression implements IExpression {
    private final IExpression operand1;
    private final IExpression operand2;
    private final ArithmeticOperation operation;

    public ArithmeticExpression(IExpression operand1, IExpression operand2, ArithmeticOperation operation) {
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operation = operation;
    }

    @Override
    public IValue evaluate(ISymbolsTable symbolsTable, IHeap heap) throws MyException {
        Integer intValue1 = ((IntValue) operand1.evaluate(symbolsTable, heap)).getValue();
        Integer intValue2 = ((IntValue) operand2.evaluate(symbolsTable, heap)).getValue();

        switch (operation) {
            case ADDITION:
                return new IntValue(intValue1 + intValue2);

            case SUBTRACTION:
                return new IntValue(intValue1 - intValue2);

            case MULTIPLICATION:
                return new IntValue(intValue1 * intValue2);

            case DIVISION:
                if (intValue2 == 0) {
                    throw new DivisionByZeroException();
                }
                return new IntValue(intValue1 / intValue2);

            default:
                throw new InvalidOperationException();
        }
    }

    @Override
    public IType typecheck(Map<String, IType> typeTable) throws MyException {
        IType type1 = operand1.typecheck(typeTable);
        IType type2 = operand2.typecheck(typeTable);

        if (type1 != null && !type1.equals(new IntType())) {
            throw new IncompatibleTypesException(new IntType(), type1);
        }

        if (type2 != null && !type2.equals(new IntType())) {
            throw new IncompatibleTypesException(new IntType(), type2);
        }

        return new IntType();
    }

    @Override
    public IExpression deepCopy() {
        return new ArithmeticExpression(operand1.deepCopy(), operand2.deepCopy(), operation);
    }

    @Override
    public String toString() {
        return operand1 + " " + operation + " " + operand2;
    }
}
