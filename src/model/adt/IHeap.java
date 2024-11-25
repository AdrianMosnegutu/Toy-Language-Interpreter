package model.adt;

import java.util.Map;
import java.util.Set;

import exceptions.NullReferenceException;
import model.values.IValue;

public interface IHeap {
    /**
     * Retrieves the value stored at the specified address in the heap.
     *
     * @param address the address in the heap where the value is stored
     * @return the value stored at the specified address
     * @throws NullReferenceException if the address does not exist in the heap
     */
    IValue getValueAt(int address) throws NullReferenceException;

    /**
     * Updates the value at the specified address in the heap.
     *
     * @param address  the address in the heap where the value should be updated
     * @param newValue the new value to set at the specified address
     * @throws NullReferenceException if the address does not exist in the heap
     */
    void setValueAt(int address, IValue newValue) throws NullReferenceException;

    /**
     * Sets the content of the heap with the provided map.
     *
     * @param content a map containing the new content for the heap, where the keys
     *                are integers representing addresses and the values are IValue
     *                instances stored at those addresses
     */
    void setContent(Map<Integer, IValue> content);

    /**
     * Retrieves the entire content of the heap.
     *
     * @return a map containing the heap's content, where the keys are the heap
     *         addresses (integers) and the values are the corresponding values
     *         stored at those addresses.
     */
    Map<Integer, IValue> getContent();

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
}
