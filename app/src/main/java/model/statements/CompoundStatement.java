package model.statements;

import java.util.Map;

import exceptions.MyException;
import model.adt.IExecutionStack;
import model.states.ProgramState;
import model.types.IType;

public class CompoundStatement implements IStatement {
    private final IStatement first;
    private final IStatement second;

    public CompoundStatement(IStatement first, IStatement second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IExecutionStack executionStack = state.getExecutionStack();

        executionStack.push(second);
        executionStack.push(first);

        return null;
    }

    @Override
    public Map<String, IType> typecheck(Map<String, IType> typeTable) throws MyException {
        return second.typecheck(first.typecheck(typeTable));
    }

    @Override
    public IStatement deepCopy() {
        return new CompoundStatement(first.deepCopy(), second.deepCopy());
    }

    @Override
    public String toString() {
        return first + "\n" + second;
    }
}
