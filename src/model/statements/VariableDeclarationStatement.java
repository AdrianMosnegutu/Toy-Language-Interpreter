package model.statements;

import java.util.Map;

import exceptions.MyException;
import exceptions.VariableExistsException;
import model.adt.ISymbolsTable;
import model.states.ProgramState;
import model.types.IType;

public class VariableDeclarationStatement implements IStatement {
    private final IType variableType;
    private final String variableName;

    public VariableDeclarationStatement(IType variableType, String variableName) {
        this.variableType = variableType;
        this.variableName = variableName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        ISymbolsTable symbolsTable = state.getSymbolsTable();

        // Check if the variable is already defined
        if (symbolsTable.isVariableDefined(variableName)) {
            throw new VariableExistsException(variableName);
        }

        symbolsTable.setVariableValue(variableName, variableType.defaultValue());
        return null;
    }

    @Override
    public Map<String, IType> typecheck(Map<String, IType> typeTable) throws MyException {
        typeTable.put(variableName, variableType);
        return typeTable;
    }

    @Override
    public IStatement deepCopy() {
        return new VariableDeclarationStatement(variableType, variableName);
    }

    @Override
    public String toString() {
        return variableType + " " + variableName + ";";
    }
}
