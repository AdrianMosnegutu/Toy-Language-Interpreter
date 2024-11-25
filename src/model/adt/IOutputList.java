package model.adt;

import java.util.List;

import exceptions.InvalidIndexException;
import model.values.IValue;

public interface IOutputList {
    /**
     * Adds a value to the output list.
     *
     * @param value the value to be added to the output list
     */
    void addValue(IValue value);

    /**
     * Retrieves the value at the specified index from the output list.
     *
     * @param index the position of the value to retrieve
     * @return the value at the specified index
     * @throws InvalidIndexException if the index is out of range
     */
    IValue getValue(int index) throws InvalidIndexException;

    /**
     * Retrieves the content of the output list.
     *
     * @return a list of IValue objects representing the content of the output list.
     */
    List<IValue> getContent();
}
