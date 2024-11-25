package model.statements;

import exceptions.MyException;
import model.states.ProgramState;

public interface IStatement {
    /**
     * Executes the current statement and returns the updated program state.
     *
     * @param state the current state of the program
     * @return the updated state of the program after executing the statement
     * @throws MyException if an error occurs during the execution of the statement
     */
    ProgramState execute(ProgramState state) throws MyException;

    /**
     * Creates and returns a deep copy of this statement.
     * 
     * A deep copy means that all objects referenced by this statement are also
     * copied, ensuring that the new statement is completely independent of the
     * original.
     *
     * @return a new IStatement object that is a deep copy of this statement.
     */
    IStatement deepCopy();
}
