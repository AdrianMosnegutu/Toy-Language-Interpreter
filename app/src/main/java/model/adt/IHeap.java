package model.adt;

import java.util.Map;
import java.util.Set;

import exceptions.NullReferenceException;
import javafx.collections.ObservableMap;
import model.values.IValue;

public interface IHeap {
    /**
     * Retrieves the value stored at the specified address in the heap.
     *
     * @param address the address in the heap where the value is stored
     * @return the value stored at the specified address
     *
     * @throws NullReferenceException if the address does not exist in the heap
     */
    IValue getValueAt(int address) throws NullReferenceException;

    /**
     * Updates the value at the specified address in the heap.
     *
     * @param address  the address in the heap where the value should be updated
     * @param newValue the new value to set at the specified address
     *
     * @throws NullReferenceException if the address does not exist in the heap
     */
    void setValueAt(int address, IValue newValue) throws NullReferenceException;

    /**
     * Retrieves a list of addresses currently stored in the heap.
     *
     * @return a List of Integer objects representing the addresses in the heap.
     */
    Set<Integer> getAddresses();

    /**
     * Allocates a new memory location for the given value in the heap and returns
     * the address of the newly allocated memory location.
     *
     * @param value the value to be stored in the heap
     * @return the address of the newly allocated memory location
     */
    int allocate(IValue value);

    /**
     * Deallocates the memory at the specified address.
     *
     * @param address the address of the memory to be deallocated
     */
    void deallocate(int address) throws NullReferenceException;

    /**
     * Returns a map of all the addresses and their values in the heap.
     *
     * @return a map of all the addresses and their values in the heap
     */
    ObservableMap<Integer, IValue> getAll();
}
