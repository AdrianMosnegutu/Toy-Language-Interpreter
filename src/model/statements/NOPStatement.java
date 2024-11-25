package model.statements;

import exceptions.MyException;
import model.states.ProgramState;

public class NOPStatement implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        return state;
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
