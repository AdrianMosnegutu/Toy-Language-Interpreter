package model.expressions;

import java.util.Map;

import exceptions.IncompatibleTypesException;
import exceptions.InvalidOperationException;
import exceptions.MyException;
import model.adt.IHeap;
import model.adt.ISymbolsTable;
import model.enums.ArithmeticRelation;
import model.types.BoolType;
import model.types.IType;
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
        Integer intValue1 = ((IntValue) operand1.evaluate(symbolsTable, heap)).getValue();
        Integer intValue2 = ((IntValue) operand2.evaluate(symbolsTable, heap)).getValue();

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
    public IType typecheck(Map<String, IType> typeTable) throws MyException {
        IType type1 = operand1.typecheck(typeTable);
        IType type2 = operand2.typecheck(typeTable);

        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new BoolType();
            } else {
                throw new IncompatibleTypesException(new BoolType(), type2);
            }
        } else {
            throw new IncompatibleTypesException(new IntType(), type1);
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
