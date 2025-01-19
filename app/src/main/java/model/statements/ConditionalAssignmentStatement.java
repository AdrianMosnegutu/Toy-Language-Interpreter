package model.statements;

import java.util.Map;

import exceptions.MyException;
import model.expressions.IExpression;
import model.states.ProgramState;
import model.types.IType;

public class ConditionalAssignmentStatement implements IStatement {
    private final String variableName;
    private final IExpression checkExpression;
    private final IExpression thenExpression;
    private final IExpression elseExpression;

    public ConditionalAssignmentStatement(String variableName, IExpression checkExpression, IExpression thenExpression,
            IExpression elseExpression) {
        this.variableName = variableName;
        this.checkExpression = checkExpression;
        this.thenExpression = thenExpression;
        this.elseExpression = elseExpression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IStatement convertedStatement = new IfStatement(
                checkExpression,
                new AssignStatement(variableName, thenExpression),
                new AssignStatement(variableName, elseExpression));

        state.getExecutionStack().push(convertedStatement);

        return null;
    }

    @Override
    public Map<String, IType> typecheck(Map<String, IType> typeTable) throws MyException {
        return typeTable;
    }

    @Override
    public IStatement deepCopy() {
        return new ConditionalAssignmentStatement(variableName, checkExpression, thenExpression, elseExpression);
    }

    @Override
    public String toString() {
        return String.format("%s = %s ? %s : %s;", variableName, checkExpression, thenExpression, elseExpression);
    }
}
