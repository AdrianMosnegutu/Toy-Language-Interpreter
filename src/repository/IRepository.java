package repository;

import exceptions.MyException;
import model.states.ProgramState;

public interface IRepository {
    /**
     * Retrieves the current state of the program.
     *
     * @return the current {@link ProgramState} instance.
     */
    ProgramState getCurrentProgram() throws MyException;

    /**
     * Logs the current state of the program.
     *
     * @param executedGarbageCollector a boolean indicating whether the garbage
     *                                 collector has been executed
     * @throws MyException if an error occurs while logging the program state.
     */
    void logProgramState(boolean executedGarbageCollector) throws MyException;
}
