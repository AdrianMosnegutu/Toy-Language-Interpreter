package controller;

import java.util.List;

import exceptions.MyException;
import model.states.ProgramState;

public interface IController {
    /**
     * Returns the list of program states for the current running application.
     *
     * @return the list of program states for the current running application
     */
    List<ProgramState> getProgramStates();

    /**
     * Executes one step of the program for all program threads.
     */
    void executeOneStep();

    /**
     * Executes all steps of the program.
     *
     * @param display a boolean indicating whether to display the steps.
     * @throws MyException if an error occurs during the execution of the steps.
     */
    void runToCompletion(boolean display);

    /**
     * Check wether the program has finished executing all its threads.
     *
     * @return true, if there are no more active threads in the program, false
     *         otherwise
     */
    boolean executionHasCompleted();
}
