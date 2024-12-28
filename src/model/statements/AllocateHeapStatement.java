package model.statements;

import java.util.Map;

import exceptions.IncompatibleTypesException;
import exceptions.MyException;
import exceptions.UndefinedVariableException;
import model.adt.IHeap;
import model.adt.ISymbolsTable;
import model.expressions.IExpression;
import model.states.ProgramState;
import model.types.IType;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

public class AllocateHeapStatement implements IStatement {
    private final String variableName;
    private final IExpression expression;

    public AllocateHeapStatement(String variableName, IExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        ISymbolsTable symbolsTable = state.getSymbolsTable();
        IHeap heap = state.getHeap();

        // Check if the variable is defined
        if (!symbolsTable.isVariableDefined(variableName)) {
            throw new UndefinedVariableException(variableName);
        }

        // Check if the expression evaluates to a value of the same type as the location
        // type inside the RefValue
        IValue expressionValue = this.expression.evaluate(symbolsTable, heap);
        RefValue value = (RefValue) symbolsTable.getVariableValue(variableName);
        if (!expressionValue.getType().equals((value).getLocationType())) {
            throw new IncompatibleTypesException((value).getLocationType(), expressionValue.getType());
        }

        value.setAddress(heap.allocate(expressionValue.deepCopy()));
        return null;
    }

    @Override
    public Map<String, IType> typecheck(Map<String, IType> typeTable) throws MyException {
        IType typeVar = typeTable.get(variableName);
        IType typeExp = expression.typecheck(typeTable);

        if (typeVar.equals(new RefType())) {
            return typeTable;
        } else {
            throw new IncompatibleTypesException(new RefType(typeExp), typeVar);
        }
    }

    @Override
    public IStatement deepCopy() {
        return new AllocateHeapStatement(variableName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("allocate(%s, %s);", variableName, expression);
    }
}
