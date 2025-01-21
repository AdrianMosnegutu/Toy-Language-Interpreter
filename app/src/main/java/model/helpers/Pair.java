package model.helpers;

public class Pair<T1, T2> {
    private final T1 first;
    private final T2 second;

    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

	public T1 getFirst() {
		return first;
	}

	public T2 getSecond() {
		return second;
	}

    @Override
    public String toString() {
        return String.format("(%s, %s)", first, second);
    }
}
