package model.types;

import model.values.BoolValue;

public class BoolType implements IType {
    @Override
    public BoolValue defaultValue() {
        return new BoolValue(false);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof BoolType;
    }

    @Override
    public String toString() {
        return "Bool";
    }
}
