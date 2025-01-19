package model.statements;

import java.util.Map;

import exceptions.IncompatibleTypesException;
import exceptions.MyException;
import model.enums.ArithmeticRelation;
import model.expressions.IExpression;
import model.expressions.RelationalExpression;
import model.states.ProgramState;
import model.types.IType;

public class SwitchCaseStatement implements IStatement {
    private final IExpression checkExpression;
    private final IExpression caseOneExpression;
    private final IStatement caseOneStatement;
    private final IExpression caseTwoExpression;
    private final IStatement caseTwoStatement;
    private final IStatement defaultStatement;

    public SwitchCaseStatement(IExpression checkExpression, IExpression caseOneExpression, IStatement caseOneStatement,
            IExpression caseTwoExpression, IStatement caseTwoStatement, IStatement defaultStatement) {
        this.checkExpression = checkExpression;
        this.caseOneExpression = caseOneExpression;
        this.caseOneStatement = caseOneStatement;
        this.caseTwoExpression = caseTwoExpression;
        this.caseTwoStatement = caseTwoStatement;
        this.defaultStatement = defaultStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IStatement convertedStatement = new IfStatement(
                new RelationalExpression(checkExpression, caseOneExpression, ArithmeticRelation.EQUAL),
                caseOneStatement,
                new IfStatement(new RelationalExpression(checkExpression, caseTwoExpression, ArithmeticRelation.EQUAL),
                        caseTwoStatement, defaultStatement));

        state.getExecutionStack().push(convertedStatement);

        return null;
    }

    @Override
    public Map<String, IType> typecheck(Map<String, IType> typeTable) throws MyException {
        caseOneStatement.typecheck(typeTable);
        caseTwoStatement.typecheck(typeTable);
        defaultStatement.typecheck(typeTable);

        IType checkType = checkExpression.typecheck(typeTable);
        IType caseOneType = caseOneExpression.typecheck(typeTable);
        IType caseTwoType = caseTwoExpression.typecheck(typeTable);

        if (caseOneType != null && !checkType.equals(caseOneType)) {
            throw new IncompatibleTypesException(checkType, caseOneType);
        }

        if (caseTwoType != null && !checkType.equals(caseTwoType)) {
            throw new IncompatibleTypesException(checkType, caseTwoType);
        }

        return typeTable;
    }

    @Override
    public IStatement deepCopy() {
        return new SwitchCaseStatement(checkExpression, caseOneExpression, caseOneStatement, caseTwoExpression,
                caseTwoStatement, defaultStatement);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("SWITCH (%s)\n", checkExpression));
        builder.append(String.format("CASE (%s):\n", caseOneExpression));
        builder.append(caseOneStatement);
        builder.append("\n");
        builder.append(String.format("CASE (%s):\n", caseTwoExpression));
        builder.append(caseTwoStatement);
        builder.append("\n");
        builder.append("DEFAULT:\n");
        builder.append(defaultStatement);
        builder.append("\nEND SWITCH");
        return builder.toString();
    }
}
