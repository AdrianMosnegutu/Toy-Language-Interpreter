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
    private final IExpression expression;

    public NegationExpression(IExpression expression) {
        this.expression = expression;
    }

	@Override
	public IValue evaluate(ISymbolsTable symbolsTable, IHeap heap) throws MyException {
        BoolValue expressionValue = (BoolValue) expression.evaluate(symbolsTable, heap);
        return new BoolValue(!expressionValue.getValue());
	}

	@Override
	public IType typecheck(Map<String, IType> symbolsTable) throws MyException {
        IType expressionType = expression.typecheck(symbolsTable);

        if (expressionType != null && !expressionType.equals(new BoolType())) {
            throw new IncompatibleTypesException(new BoolType(), expressionType);
        }
        
        return new BoolType();
	}

	@Override
	public IExpression deepCopy() {
        return new NegationExpression(expression);
	}

    @Override
    public String toString() {
        return String.format("!(%s)", expression);
    }
}
