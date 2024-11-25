package model.types;

import model.values.IValue;

public interface IType {
    /**
     * Returns the default value associated with this type.
     *
     * @return the default value of this type as an {@code IValue} instance.
     */
    IValue defaultValue();
}
