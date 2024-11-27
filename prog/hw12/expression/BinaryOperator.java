package expression;

import java.util.Objects;

public abstract class BinaryOperator implements Expression {
    private final String value;
    private final int priority;
    private final Expression operand1;
    private final Expression operand2;

    public BinaryOperator(String value, int priority, Expression operand1, Expression operand2) {
        this.value = value;
        this.priority = priority;
        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    @Override
    public int evaluate(int x) {
        return calculate(operand1.evaluate(x), operand2.evaluate(x));
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "(" +
                operand1.toString() +
                value +
                operand2.toString() +
                ")";
    }

    public abstract int calculate(int x, int y);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BinaryOperator that = (BinaryOperator) o;
        return priority == that.priority
                && Objects.equals(value, that.value)
                && operand1.equals(that.operand1)
                && operand2.equals(that.operand2);
    }

    @Override
    public int hashCode() {
        int operand1Hash = (operand1 == null) ? 0 : operand1.hashCode();
        int operand2Hash = (operand2 == null) ? 0 : operand2.hashCode();
        return ((((17 + value.hashCode() + priority) * 31) + operand1Hash) * 31 + operand2Hash) * 31;
    }
}
