package controller;

import java.util.List;

import exceptions.MyException;
import model.states.ProgramState;

public interface IController {
    List<ProgramState> getProgramStates();

    void oneStepAllPrograms();

    /**
     * Executes all steps of the program.
     *
     * @param display a boolean indicating whether to display the steps.
     * @throws MyException if an error occurs during the execution of the steps.
     */
    void allStep(boolean display);
}
