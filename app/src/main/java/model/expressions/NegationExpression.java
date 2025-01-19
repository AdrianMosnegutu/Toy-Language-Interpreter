package model.expressions;

import java.util.Map;

import exceptions.IncompatibleTypesException;
import exceptions.MyException;
import model.adt.IHeap;
import model.adt.ISymbolsTable;
import model.types.BoolType;
import model.types.IType;
import model.values.BoolValue;
import model.values.IValue;

public class NegationExpression implements IExpression {
    private final IExpression operandExpression;

    public NegationExpression(IExpression operandExpression) {
        this.operandExpression = operandExpression;
    }

	@Override
	public IValue evaluate(ISymbolsTable symbolsTable, IHeap heap) throws MyException {
        BoolValue expressionValue = (BoolValue) operandExpression.evaluate(symbolsTable, heap);
        return new BoolValue(!expressionValue.getValue());
	}

	@Override
	public IType typecheck(Map<String, IType> symbolsTable) throws MyException {
        IType expressionType = operandExpression.typecheck(symbolsTable);

        if (expressionType != null && !expressionType.equals(new BoolType())) {
            throw new IncompatibleTypesException(new BoolType(), expressionType);
        }

        return new BoolType();
	}

	@Override
	public IExpression deepCopy() {
        return new NegationExpression(operandExpression);
	}

    @Override
    public String toString() {
        return String.format("!(%s)", operandExpression);
    }
}
