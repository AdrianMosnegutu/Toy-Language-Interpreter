package model.adt;

import javafx.collections.ObservableList;
import model.values.IValue;

public interface IOutputList {
    /**
     * Adds a value to the output list.
     *
     * @param value the value to be added to the output list
     */
    void addValue(IValue value);

    /**
     * Retrieves the content of the output list.
     *
     * @return a list of IValue objects representing the content of the output list.
     */
    ObservableList<IValue> getAll();
}
