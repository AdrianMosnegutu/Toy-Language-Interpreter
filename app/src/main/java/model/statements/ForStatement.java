package model.statements;

import java.util.Map;

import exceptions.IncompatibleTypesException;
import exceptions.MyException;
import model.enums.ArithmeticRelation;
import model.expressions.IExpression;
import model.expressions.RelationalExpression;
import model.expressions.VariableExpression;
import model.states.ProgramState;
import model.types.IType;
import model.types.IntType;

public class ForStatement implements IStatement {
    private final String innerVariableName;
    private final IExpression startExpression;
    private final IExpression validExpression;
    private final IExpression stepExpression;
    private final IStatement innerStatement;

    public ForStatement(String innerVariableName, IExpression startExpression, IExpression validExpression,
            IExpression stepExpression, IStatement innerStatement) {
        this.innerVariableName = innerVariableName;
        this.startExpression = startExpression;
        this.validExpression = validExpression;
        this.stepExpression = stepExpression;
        this.innerStatement = innerStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IStatement convertedStatement = new CompoundStatement(
                new VariableDeclarationStatement(new IntType(), innerVariableName),
                new CompoundStatement(
                        new AssignStatement(innerVariableName, startExpression),
                        new WhileStatement(
                                new RelationalExpression(new VariableExpression(innerVariableName), validExpression,
                                        ArithmeticRelation.LESS_THAN),
                                new CompoundStatement(
                                        innerStatement,
                                        new AssignStatement(innerVariableName, stepExpression)))));

        state.getExecutionStack().push(convertedStatement);
        return null;
    }

    @Override
    public Map<String, IType> typecheck(Map<String, IType> typeTable) throws MyException {
        innerStatement.typecheck(typeTable);
        
        IType startExpressionType = startExpression.typecheck(typeTable);
        IType validExpressionType = validExpression.typecheck(typeTable);
        IType stepExpressionType = stepExpression.typecheck(typeTable);

        if (startExpressionType != null && !startExpressionType.equals(new IntType())) {
            throw new IncompatibleTypesException(new IntType(), startExpressionType);
        }

        if (validExpressionType != null && !validExpressionType.equals(new IntType())) {
            throw new IncompatibleTypesException(new IntType(), validExpressionType);
        }

        if (stepExpressionType != null && !stepExpressionType.equals(new IntType())) {
            throw new IncompatibleTypesException(new IntType(), stepExpressionType);
        }

        return typeTable;
    }

    @Override
    public IStatement deepCopy() {
        return new ForStatement(innerVariableName, startExpression, validExpression, stepExpression, innerStatement);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(String.format("FOR (%s = %s; %s < %s; %s = %s)\n",
                innerVariableName,
                startExpression,
                innerVariableName,
                validExpression,
                innerVariableName,
                stepExpression));
        builder.append(innerStatement.toString() + "\n");
        builder.append("END FOR");

        return builder.toString();
    }
}
