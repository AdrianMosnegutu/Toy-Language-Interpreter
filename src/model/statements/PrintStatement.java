package model.statements;

import exceptions.MyException;
import model.expressions.IExpression;
import model.states.ProgramState;

public class PrintStatement implements IStatement {
    private final IExpression expression;

    public PrintStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        state.getOutput().addValue(expression.evaluate(state.getSymbolsTable(), state.getHeap()));
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new PrintStatement(expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("print(%s);", expression);
    }
}
