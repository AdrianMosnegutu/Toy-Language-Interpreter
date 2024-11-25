package model.statements;

import exceptions.IncompatibleTypesException;
import exceptions.MyException;
import model.adt.IExecutionStack;
import model.expressions.IExpression;
import model.states.ProgramState;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.IValue;

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

        // Check if the expression evaluates to a BoolValue
        IValue value = expression.evaluate(state.getSymbolsTable(), state.getHeap());
        if (!value.getType().equals(new BoolType())) {
            throw new IncompatibleTypesException(new BoolType(), value.getType());
        }

        // Push the inner statement on the stack if the condition is met, followed by
        // the while statement itself to allow for multiple iterations
        if (((BoolValue) value).getValue()) {
            executionStack.push(this);
            executionStack.push(inner);
        }

        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new WhileStatement(expression.deepCopy(), inner);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("WHILE (%s) DO\n", expression));
        builder.append(String.format("    %s\n", inner));
        builder.append("    END WHILE");
        return builder.toString();
    }
}
