package model.adt;

import exceptions.EmptyStackException;
import javafx.collections.ObservableList;
import model.statements.IStatement;

public interface IExecutionStack {
    /**
     * Removes and returns the statement at the top of the execution stack.
     *
     * @return the statement at the top of the stack
     * @throws EmptyStackException if the stack is empty
     */
    IStatement pop() throws EmptyStackException;

    /**
     * Pushes a statement onto the execution stack.
     *
     * @param statement the statement to be pushed onto the stack
     */
    void push(IStatement statement);

    /**
     * Checks if the execution stack is empty.
     *
     * @return true if the stack is empty, false otherwise
     */
    boolean isEmpty();

    ObservableList<IStatement> getAll();
}
