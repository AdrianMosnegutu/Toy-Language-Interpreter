package model.statements;

import java.util.Map;

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

        if (!symbolsTable.isVariableDefined(variableName)) {
            throw new UndefinedVariableException(variableName);
        }

        IValue expressionValue = assignedExpression.evaluate(symbolsTable, state.getHeap());
        symbolsTable.setVariableValue(variableName, expressionValue);

        return null;
    }

    @Override
    public Map<String, IType> typecheck(Map<String, IType> typeTable) throws MyException {
        IType typeVar = typeTable.get(variableName);
        IType typeExp = assignedExpression.typecheck(typeTable);

        if (!typeVar.equals(typeExp)) {
            throw new IncompatibleTypesException(typeVar, typeExp);
        }

        return typeTable;
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
