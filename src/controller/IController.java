package controller;

import exceptions.MyException;

public interface IController {
    /**
     * Executes all steps of the program.
     *
     * @param display a boolean indicating whether to display the steps.
     * @throws MyException if an error occurs during the execution of the steps.
     */
    void allStep(boolean display);
}
