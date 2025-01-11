package model.adt;

import java.io.BufferedReader;
import java.util.List;

import exceptions.UndefinedFileException;
import model.values.StringValue;

public interface IFileTable {
    /**
     * Retrieves the BufferedReader associated with the given file name.
     *
     * @param fileName the name of the file for which the BufferedReader is to be
     *                 retrieved
     * @return the BufferedReader associated with the specified file name
     *
     * @throws UndefinedFileException if the file name is not found in the file
     *                                table
     */
    BufferedReader getFile(StringValue fileName) throws UndefinedFileException;

    /**
     * Associates the specified BufferedReader with the specified file name in the
     * file table.
     *
     * @param fileName the name of the file to be associated with the BufferedReader
     * @param file     the BufferedReader to be associated with the specified file
     *                 name
     */
    void setFile(StringValue fileName, BufferedReader file);

    /**
     * Checks if the file represented by the given StringValue is currently open.
     *
     * @param file the StringValue representing the file to check
     * @return true if the file is open, false otherwise
     */
    boolean isOpen(StringValue file);

    /**
     * Closes the file associated with the given file name.
     *
     * @param fileName the name of the file to be closed
     * 
     * @throws UndefinedFileException if the file with the given name is not defined
     *                                in the file table
     */
    void closeFile(StringValue fileName) throws UndefinedFileException;

    List<StringValue> getAll();
}
