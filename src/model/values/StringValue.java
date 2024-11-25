package model.values;

import model.types.IType;
import model.types.StringType;

public class StringValue implements IValue {
    private String value;

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof StringValue)) {
            return false;
        }

        return this.value.equals(((StringValue) other).getValue());
    }

    @Override
    public IValue deepCopy() {
        return new StringValue(this.value);
    }

    @Override
    public String toString() {
        return '"' + this.value + '"';
    }
}
