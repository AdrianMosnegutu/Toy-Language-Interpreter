package repository;

import java.util.List;

import exceptions.MyException;
import model.states.ProgramState;

public interface IRepository {
    /**
     * Check wether the repository is empty.
     *
     * @return true, if the repository is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Retrieves the list of program threads.
     *
     * @return a list of ProgramState objects representing the current program
     *         threads.
     */
    List<ProgramState> getProgramThreads();

    /**
     * Sets the list of program threads.
     *
     * @param programThreads the list of ProgramState objects representing the
     *                       program threads to be set
     */
    void setProgramThreads(List<ProgramState> programThreads);

    /**
     * Logs the current state of the program.
     *
     * @param program the current state of the program to be logged
     *
     * @throws MyException if an error occurs during logging
     */
    void logProgramState(ProgramState program) throws MyException;
}
