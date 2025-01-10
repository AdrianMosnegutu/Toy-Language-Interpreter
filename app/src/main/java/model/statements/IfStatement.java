package model.statements;

import java.util.HashMap;
import java.util.Map;

import exceptions.IncompatibleTypesException;
import exceptions.MyException;
import model.adt.IExecutionStack;
import model.adt.ISymbolsTable;
import model.expressions.IExpression;
import model.states.ProgramState;
import model.types.BoolType;
import model.types.IType;
import model.values.BoolValue;

public class IfStatement implements IStatement {
    private final IExpression expression;
    private final IStatement then;
    private final IStatement otherwise;

    public IfStatement(IExpression expression, IStatement then, IStatement otherwise) {
        this.expression = expression;
        this.then = then;
        this.otherwise = otherwise;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IExecutionStack executionStack = state.getExecutionStack();
        ISymbolsTable symbolsTable = state.getSymbolsTable();

        Boolean conditionMet = ((BoolValue) expression.evaluate(symbolsTable, state.getHeap())).getValue();
        executionStack.push(conditionMet ? then : otherwise);

        return null;
    }

    @Override
    public Map<String, IType> typecheck(Map<String, IType> typeTable) throws MyException {
        IType typeExp = expression.typecheck(typeTable);

        if (typeExp.equals(new BoolType())) {
            then.typecheck(new HashMap<>(typeTable));
            otherwise.typecheck(new HashMap<>(typeTable));
            return typeTable;
        } else {
            throw new IncompatibleTypesException(new BoolType(), typeExp);
        }
    }

    @Override
    public IStatement deepCopy() {
        return new IfStatement(expression.deepCopy(), then.deepCopy(), otherwise.deepCopy());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("IF (%s) THEN\n", expression));
        builder.append(String.format("    %s\n", then));
        builder.append(String.format("    ELSE\n"));
        builder.append(String.format("    %s\n", otherwise));
        builder.append("    END IF");
        return builder.toString();
    }
}
