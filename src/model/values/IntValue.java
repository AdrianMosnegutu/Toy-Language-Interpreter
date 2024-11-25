package model.values;

import model.types.IType;
import model.types.IntType;

public class IntValue implements IValue {
    private int value;

    public IntValue(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof IntValue)) {
            return false;
        }

        return this.value == ((IntValue) other).getValue();
    }

    @Override
    public IValue deepCopy() {
        return new IntValue(this.value);
    }

    @Override
    public String toString() {
        return Integer.toString(this.value);
    }
}
