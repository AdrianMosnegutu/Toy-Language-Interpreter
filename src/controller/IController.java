package controller;

import exceptions.MyException;
import model.states.ProgramState;

public interface IController {
    /**
     * Executes one step of the given program state.
     *
     * @param program the current state of the program to execute one step on
     * @return the new state of the program after executing one step
     * @throws MyException if an error occurs during the execution of the step
     */
    ProgramState oneStep(ProgramState program) throws MyException;

    /**
     * Executes all steps of the program.
     *
     * @param display a boolean indicating whether to display the steps.
     * @throws MyException if an error occurs during the execution of the steps.
     */
    void allStep(boolean display) throws MyException;

    /**
     * Retrieves the output log.
     *
     * @return A string representing the output log.
     * @throws MyException if an error occurs while fetching the output log.
     */
    String getOutputLog() throws MyException;
}
