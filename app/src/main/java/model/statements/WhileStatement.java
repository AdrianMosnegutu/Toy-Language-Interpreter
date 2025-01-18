package model.statements;

import java.util.HashMap;
import java.util.Map;

import exceptions.IncompatibleTypesException;
import exceptions.MyException;
import model.adt.IExecutionStack;
import model.expressions.IExpression;
import model.states.ProgramState;
import model.types.BoolType;
import model.types.IType;
import model.values.BoolValue;

public class WhileStatement implements IStatement {
    private final IExpression expression;
    private final IStatement inner;

    public WhileStatement(IExpression expression, IStatement inner) {
        this.expression = expression;
        this.inner = inner;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IExecutionStack executionStack = state.getExecutionStack();

        BoolValue expressionValue = (BoolValue) expression.evaluate(state.getSymbolsTable(), state.getHeap());
        if (expressionValue.getValue()) {
            executionStack.push(this);
            executionStack.push(inner);
        }

        return null;
    }

    @Override
    public Map<String, IType> typecheck(Map<String, IType> typeTable) throws MyException {
        inner.typecheck(new HashMap<>(typeTable));
        IType typeExp = expression.typecheck(typeTable);

        if (!typeExp.equals(new BoolType())) {
            throw new IncompatibleTypesException(new BoolType(), typeExp);
        }

        return typeTable;
    }

    @Override
    public IStatement deepCopy() {
        return new WhileStatement(expression.deepCopy(), inner);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("WHILE (%s) DO\n", expression));
        builder.append(String.format("%s\n", inner));
        builder.append("END WHILE");
        return builder.toString();
    }
}
