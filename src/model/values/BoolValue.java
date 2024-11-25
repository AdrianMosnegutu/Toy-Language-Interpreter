package model.values;

import model.types.BoolType;
import model.types.IType;

public class BoolValue implements IValue {
    private boolean value;

    public BoolValue(boolean value) {
        this.value = value;
    }

    @Override
    public Boolean getValue() {
        return this.value;
    }

    @Override
    public IType getType() {
        return new BoolType();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof BoolValue)) {
            return false;
        }

        return this.value == ((BoolValue) other).getValue();
    }

    @Override
    public IValue deepCopy() {
        return new BoolValue(this.value);
    }

    @Override
    public String toString() {
        return Boolean.toString(this.value);
    }
}
