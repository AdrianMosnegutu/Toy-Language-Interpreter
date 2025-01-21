package model.statements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import exceptions.IncompatibleTypesException;
import exceptions.MyException;
import exceptions.UndefinedVariableException;
import model.expressions.IExpression;
import model.helpers.Pair;
import model.states.ProgramState;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

public class NewBarrierStatement implements IStatement {
    private final String variableName;
    private final IExpression expression;

    public NewBarrierStatement(String variableName, IExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IntValue barrierCounter = (IntValue) expression.evaluate(state.getSymbolsTable(), state.getHeap());

        int freeLocation = state.getBarrierTable().createBarrier(
                new Pair<Integer, List<Integer>>(barrierCounter.getValue(), new ArrayList<Integer>()));

        if (!state.getSymbolsTable().isVariableDefined(variableName)) {
            throw new UndefinedVariableException(variableName);
        }

        IValue variableValue = state.getSymbolsTable().getVariableValue(variableName);
        if (!variableValue.getType().equals(new IntType())) {
            throw new IncompatibleTypesException(new IntType(), variableValue.getType());
        }

        state.getSymbolsTable().setVariableValue(variableName, new IntValue(freeLocation));

        return null;
    }

    @Override
    public Map<String, IType> typecheck(Map<String, IType> typeTable) throws MyException {
        IType expressionType = expression.typecheck(typeTable);

        if (typeTable.containsKey(variableName) && !typeTable.get(variableName).equals(new IntType())) {
            throw new IncompatibleTypesException(new IntType(), typeTable.get(variableName));
        }

        if (expressionType != null && !expressionType.equals(new IntType())) {
            throw new IncompatibleTypesException(new IntType(), expressionType);
        }

        return typeTable;
    }

    @Override
    public IStatement deepCopy() {
        return new NewBarrierStatement(variableName, expression);
    }

    @Override
    public String toString() {
        return String.format("newBarrier(%s, %s);", variableName, expression);
    }
}
