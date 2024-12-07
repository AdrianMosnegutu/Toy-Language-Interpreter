package model.statements;

import exceptions.IncompatibleTypesException;
import exceptions.MyException;
import model.adt.IExecutionStack;
import model.adt.ISymbolsTable;
import model.expressions.IExpression;
import model.states.ProgramState;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.IValue;

public class IfStatement implements IStatement {
    private final IExpression expression;
    private final IStatement then;
    private final IStatement otherwise;

    public IfStatement(IExpression expression, IStatement then, IStatement otherwise) {
        this.expression = expression;
        this.then = then;
        this.otherwise = otherwise;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IExecutionStack executionStack = state.getExecutionStack();
        ISymbolsTable symbolsTable = state.getSymbolsTable();

        // Check if the expression evaluates to a BoolValue
        IValue condition = this.expression.evaluate(symbolsTable, state.getHeap());
        if (!condition.getType().equals(new BoolType())) {
            throw new IncompatibleTypesException(new BoolType(), condition.getType());
        }

        Boolean conditionMet = ((BoolValue) condition).getValue();
        executionStack.push(conditionMet ? then : otherwise);

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new IfStatement(expression.deepCopy(), then.deepCopy(), otherwise.deepCopy());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("IF (%s) THEN\n", expression));
        builder.append(String.format("    %s\n", then));
        builder.append(String.format("    ELSE\n"));
        builder.append(String.format("    %s\n", otherwise));
        builder.append("    END IF");
        return builder.toString();
    }
}
