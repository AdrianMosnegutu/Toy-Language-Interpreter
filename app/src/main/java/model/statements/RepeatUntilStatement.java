package model.statements;

import java.util.Map;

import exceptions.IncompatibleTypesException;
import exceptions.MyException;
import model.expressions.IExpression;
import model.expressions.NegationExpression;
import model.states.ProgramState;
import model.types.BoolType;
import model.types.IType;

public class RepeatUntilStatement implements IStatement {
    private final IStatement innerStatement;
    private final IExpression expression;

    public RepeatUntilStatement(IStatement innerStatement, IExpression expression) {
        this.innerStatement = innerStatement;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IStatement newStatement = new CompoundStatement(
                innerStatement,
                new WhileStatement(new NegationExpression(expression), innerStatement));

        state.getExecutionStack().push(newStatement);

        return null;
    }

    @Override
    public Map<String, IType> typecheck(Map<String, IType> typeTable) throws MyException {
        innerStatement.typecheck(typeTable);
        IType expressionType = expression.typecheck(typeTable);

        if (expressionType != null && !expressionType.equals(new BoolType())) {
            throw new IncompatibleTypesException(new BoolType(), expressionType);
        }

        return typeTable;
    }

    @Override
    public IStatement deepCopy() {
        return new RepeatUntilStatement(innerStatement, expression);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("REPEAT\n");
        builder.append(innerStatement + "\n");
        builder.append(String.format("UNTIL (%s)", expression));
        return builder.toString();
    }
}
