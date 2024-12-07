package model.statements;

import exceptions.IncompatibleTypesException;
import exceptions.MyException;
import exceptions.UndefinedVariableException;
import model.adt.ISymbolsTable;
import model.expressions.IExpression;
import model.states.ProgramState;
import model.types.IType;
import model.values.IValue;

public class AssignStatement implements IStatement {
    private final String variableName;
    private final IExpression assignedExpression;

    public AssignStatement(String variableName, IExpression assignedExpression) {
        this.variableName = variableName;
        this.assignedExpression = assignedExpression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        ISymbolsTable symbolsTable = state.getSymbolsTable();

        // Check if the variable is defined
        if (!symbolsTable.isVariableDefined(variableName)) {
            throw new UndefinedVariableException(variableName);
        }

        IValue expressionResult = assignedExpression.evaluate(symbolsTable, state.getHeap());

        IType variableType = symbolsTable.getVariableValue(variableName).getType();
        IType expressionResultType = expressionResult.getType();

        // Check if the expression evaluates to a value of the same type as the variable
        if (!variableType.equals(expressionResultType)) {
            throw new IncompatibleTypesException(variableType, expressionResultType);
        }

        symbolsTable.setVariableValue(variableName, expressionResult);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new AssignStatement(variableName, assignedExpression.deepCopy());
    }

    @Override
    public String toString() {
        return variableName + " = " + assignedExpression + ";";
    }
}
