package model.statements;

import java.util.Map;

import exceptions.MyException;
import model.states.ProgramState;
import model.types.IType;

public class NOPStatement implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        return null;
    }

    @Override
    public Map<String, IType> typecheck(Map<String, IType> typeTable) throws MyException {
        return typeTable;
    }

    @Override
    public IStatement deepCopy() {
        return new NOPStatement();
    }

    @Override
    public String toString() {
        return "pass";
    }
}
