package model.types;

import model.values.IValue;
import model.values.RefValue;

public class RefType implements IType {
    private final IType inner;

    public RefType() {
        this.inner = null;
    }

    public RefType(IType inner) {
        this.inner = inner;
    }

    /**
     * Retrieves the type of the variable that the reference points to.
     *
     * @return the type of the variable that the reference points to.
     */
    public IType getInner() {
        return this.inner;
    }

    @Override
    public IValue defaultValue() {
        return new RefValue(0, inner);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof RefType)) {
            return false;
        }

        if (this.inner == null || ((RefType) other).getInner() == null) {
            return true;
        }

        return this.inner.equals(((RefType) other).getInner());
    }

    @Override
    public String toString() {
        return inner != null ? inner + "*" : "Ref";
    }
}
