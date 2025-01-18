package controller;

import exceptions.MyException;
import javafx.collections.ObservableList;
import model.states.ProgramState;

public interface IController {
    ObservableList<ProgramState> getProgramStates();

    /**
     * Executes one step of the program for all program threads.
     */
    void oneStep();

    /**
     * Executes all steps of the program.
     *
     * @param display a boolean indicating whether to display the steps.
     * @throws MyException if an error occurs during the execution of the steps.
     */
    void allStep(boolean display);
}
