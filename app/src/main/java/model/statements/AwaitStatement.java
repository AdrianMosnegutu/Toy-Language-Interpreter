package model.statements;

import java.util.List;
import java.util.Map;

import exceptions.IncompatibleTypesException;
import exceptions.MyException;
import model.expressions.IExpression;
import model.helpers.Pair;
import model.states.ProgramState;
import model.types.IType;
import model.types.IntType;
import model.values.IntValue;

public class AwaitStatement implements IStatement {
    private final IExpression variableExpression;

    public AwaitStatement(IExpression variableExpression) {
        this.variableExpression = variableExpression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IntValue freeLocation = (IntValue) variableExpression.evaluate(state.getSymbolsTable(), state.getHeap());

        if (!state.getBarrierTable().barrierExists(freeLocation.getValue())) {
            throw new IndexOutOfBoundsException(String.format("Barrier at %s doesn't exist!", freeLocation));
        }

        Pair<Integer, List<Integer>> barrier = state.getBarrierTable().getBarrier(freeLocation.getValue());
        Integer counter = barrier.getFirst();
        List<Integer> pidsList = barrier.getSecond();

        if (counter > pidsList.size()) {
            if (!pidsList.contains(state.getPid())) {
                pidsList.add(state.getPid());
            }

            state.getExecutionStack().push(new AwaitStatement(variableExpression));
        }

        return null;
    }

    @Override
    public Map<String, IType> typecheck(Map<String, IType> typeTable) throws MyException {
        IType expressionType = variableExpression.typecheck(typeTable);

        if (expressionType != null && !expressionType.equals(new IntType())) {
            throw new IncompatibleTypesException(new IntType(), expressionType);
        }

        return typeTable;
    }

    @Override
    public IStatement deepCopy() {
        return new AwaitStatement(variableExpression);
    }

    @Override
    public String toString() {
        return String.format("await(%s);", variableExpression);
    }
}
