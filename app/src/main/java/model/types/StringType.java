package model.types;

import model.values.StringValue;

public class StringType implements IType {
    @Override
    public StringValue defaultValue() {
        return new StringValue("");
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof StringType;
    }

    @Override
    public String toString() {
        return "String";
    }
}
