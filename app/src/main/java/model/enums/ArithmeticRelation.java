package model.enums;

public enum ArithmeticRelation {
    LESS_THAN("<"),
    LESS_THAN_OR_EQUAL("<="),
    EQUAL("=="),
    NOT_EQUAL("!="),
    GREATER_THAN(">"),
    GREATER_THAN_OR_EQUAL(">=");

    public final String expression;

    private ArithmeticRelation(String expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return String.valueOf(expression);
    }
}
