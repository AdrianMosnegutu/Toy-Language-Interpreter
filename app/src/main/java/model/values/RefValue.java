package model.values;

import model.types.IType;
import model.types.RefType;

public class RefValue implements IValue {
    private int address;
    private final IType locationType;

    public RefValue(int address, IType locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress() {
        return this.address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public IType getLocationType() {
        return this.locationType;
    }

    @Override
    public IValue getValue() {
        return null;
    }

    @Override
    public IType getType() {
        return new RefType(locationType);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof RefValue)) {
            return false;
        }

        if (this.address != ((RefValue) other).getAddress()) {
            return false;
        }

        return this.locationType.equals(((RefValue) other).getLocationType());
    }

    @Override
    public IValue deepCopy() {
        return new RefValue(address, locationType);
    }

    @Override
    public String toString() {
        return String.format("(%s -> %s)", address, locationType);
    }
}
