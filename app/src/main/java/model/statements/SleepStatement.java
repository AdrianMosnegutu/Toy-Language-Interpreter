package model.statements;

import java.util.Map;

import exceptions.IncompatibleTypesException;
import exceptions.MyException;
import model.expressions.IExpression;
import model.expressions.ValueExpression;
import model.states.ProgramState;
import model.types.IType;
import model.types.IntType;
import model.values.IntValue;

public class SleepStatement implements IStatement {
    private final IExpression sleepAmountExpression;

    public SleepStatement(IExpression sleepAmountExpression) {
        this.sleepAmountExpression = sleepAmountExpression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IntValue sleepAmountValue = (IntValue) sleepAmountExpression.evaluate(state.getSymbolsTable(), state.getHeap());

        if (sleepAmountValue.getValue() > 0) {
            state.getExecutionStack()
                    .push(new SleepStatement(new ValueExpression(new IntValue(sleepAmountValue.getValue() - 1))));
        }

        return null;
    }

    @Override
    public Map<String, IType> typecheck(Map<String, IType> typeTable) throws MyException {
        IType expressionType = sleepAmountExpression.typecheck(typeTable);

        if (expressionType != null && !expressionType.equals(new IntType())) {
            throw new IncompatibleTypesException(new IntType(), expressionType);
        }

        return typeTable;
    }

    @Override
    public IStatement deepCopy() {
        return new SleepStatement(sleepAmountExpression);
    }

    @Override
    public String toString() {
        return String.format("SLEEP(%s);", sleepAmountExpression);
    }
}
