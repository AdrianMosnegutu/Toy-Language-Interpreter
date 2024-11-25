package model.adt;

import java.util.Map;
import java.util.Set;

import exceptions.UndefinedVariableException;
import model.values.IValue;

public interface ISymbolsTable {
    /**
     * Retrieves the value of a variable from the symbols table.
     *
     * @param variableName the name of the variable whose value is to be retrieved
     * @return the value associated with the specified variable name
     * @throws UndefinedVariableException if the variable name is not defined in the
     *                                    symbols table
     */
    IValue getVariableValue(String variableName) throws UndefinedVariableException;

    /**
     * Sets the value of a variable in the symbols table.
     *
     * @param variableName the name of the variable to be set
     * @param value        the value to be assigned to the variable
     */
    void setVariableValue(String variableName, IValue value);

    /**
     * Retrieves a set of all variable names stored in the symbol table.
     *
     * @return a Set containing the names of all variables.
     */
    Set<String> getVariableNames();

    /**
     * Retrieves the entire content of the symbols table.
     *
     * @return a map containing all the entries in the symbols table, where the keys
     *         are variable names and the values are the corresponding IValue
     *         instances.
     */
    Map<String, IValue> getContent();

    /**
     * Checks if a variable is defined in the symbol table.
     *
     * @param variableName the name of the variable to check
     * @return true if the variable is defined, false otherwise
     */
    boolean isVariableDefined(String variableName);

    /**
     * Deletes a variable from the symbols table.
     *
     * @param variableName the name of the variable to be deleted
     * @throws UndefinedVariableException if the variable does not exist in the
     *                                    symbols table
     */
    void deleteVariable(String variableName) throws UndefinedVariableException;
}
