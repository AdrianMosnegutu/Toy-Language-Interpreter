package model.types;

import model.values.IntValue;

public class IntType implements IType {
    @Override
    public IntValue defaultValue() {
        return new IntValue(0);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof IntType;
    }

    @Override
    public String toString() {
        return "Int";
    }
}
