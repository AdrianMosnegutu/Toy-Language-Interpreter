package repository;

import exceptions.MyException;
import javafx.collections.ObservableList;
import model.states.ProgramState;

public interface IRepository {
    /**
     * Retrieves the list of program threads.
     *
     * @return a list of ProgramState objects representing the current program
     *         threads.
     */
    ObservableList<ProgramState> getProgramThreads();

    /**
     * Sets the list of program threads.
     *
     * @param programThreads the list of ProgramState objects representing the
     *                       program threads to be set
     */
    void setProgramThreads(ObservableList<ProgramState> programThreads);

    /**
     * Logs the current state of the program.
     *
     * @param program the current state of the program to be logged
     *
     * @throws MyException if an error occurs during logging
     */
    void logProgramState(ProgramState program) throws MyException;
}
