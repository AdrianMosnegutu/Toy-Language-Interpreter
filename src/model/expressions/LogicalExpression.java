package model.expressions;

import java.util.Map;

import exceptions.IncompatibleTypesException;
import exceptions.InvalidOperationException;
import exceptions.MyException;
import model.adt.IHeap;
import model.adt.ISymbolsTable;
import model.enums.LogicalOperation;
import model.types.BoolType;
import model.types.IType;
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
        Boolean boolValue1 = ((BoolValue) operand1.evaluate(symbolsTable, heap)).getValue();
        Boolean boolValue2 = ((BoolValue) operand2.evaluate(symbolsTable, heap)).getValue();

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
    public IType typecheck(Map<String, IType> typeTable) throws MyException {
        IType type1 = operand1.typecheck(typeTable);
        IType type2 = operand2.typecheck(typeTable);

        if (type1.equals(new BoolType())) {
            if (type2.equals(new BoolType())) {
                return new BoolType();
            } else {
                throw new IncompatibleTypesException(new BoolType(), type2);
            }
        } else {
            throw new IncompatibleTypesException(new BoolType(), type1);
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
