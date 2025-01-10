package model.values;

import model.types.IType;

public interface IValue {
    /**
     * Retrieves the value represented by this instance.
     *
     * @return the value as an Object.
     */
    Object getValue();

    /**
     * Retrieves the type of the value.
     *
     * @return the type of the value as an instance of IType.
     */
    IType getType();

    /**
     * Creates and returns a deep copy of this IValue instance.
     * A deep copy means that all objects referenced by this instance
     * are also copied, rather than just copying the references.
     *
     * @return a new IValue instance that is a deep copy of this instance
     */
    IValue deepCopy();
}
