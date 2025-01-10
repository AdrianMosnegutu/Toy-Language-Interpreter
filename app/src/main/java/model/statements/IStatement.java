package model.statements;

import java.util.Map;

import exceptions.MyException;
import model.states.ProgramState;
import model.types.IType;

public interface IStatement {
    /**
     * Executes the current statement and returns the updated program state.
     *
     * @param state the current state of the program
     * @return the updated state of the program after executing the statement
     *
     * @throws MyException if an error occurs during the execution of the statement
     */
    ProgramState execute(ProgramState state) throws MyException;

    /**
     * Performs type checking on the statement.
     *
     * @param typeTable a map containing the current type environment, where the
     *                  keys are variable names and the values are their
     *                  corresponding types.
     * @return a map representing the updated type environment after type checking
     *         the statement.
     *
     * @throws MyException if a type error is encountered during type checking.
     */
    Map<String, IType> typecheck(Map<String, IType> typeTable) throws MyException;

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
