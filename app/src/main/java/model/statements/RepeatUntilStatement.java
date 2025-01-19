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
    private final IExpression endConditionExpression;

    public RepeatUntilStatement(IStatement innerStatement, IExpression endConditionExpression) {
        this.innerStatement = innerStatement;
        this.endConditionExpression = endConditionExpression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IStatement convertedStatement = new CompoundStatement(innerStatement,
                new WhileStatement(new NegationExpression(endConditionExpression), innerStatement));

        state.getExecutionStack().push(convertedStatement);

        return null;
    }

    @Override
    public Map<String, IType> typecheck(Map<String, IType> typeTable) throws MyException {
        innerStatement.typecheck(typeTable);

        IType expressionType = endConditionExpression.typecheck(typeTable);
        if (expressionType != null && !expressionType.equals(new BoolType())) {
            throw new IncompatibleTypesException(new BoolType(), expressionType);
        }

        return typeTable;
    }

    @Override
    public IStatement deepCopy() {
        return new RepeatUntilStatement(innerStatement, endConditionExpression);
    }

    @Override
    public String toString() {
        return String.format("REPEAT\n%s\nUNTIL %s", innerStatement, endConditionExpression);
    }
}
