package expression;

import java.util.Objects;

public abstract class UnaryOperator implements VariableExpression {
    private final String value;
    private final int priority;
    private final VariableExpression operand;

    public UnaryOperator(String value, int priority, VariableExpression operand) {
        this.value = value;
        this.priority = priority;
        this.operand = operand;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int evaluate(int x) {
        return calculate(operand.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return calculate(operand.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return "(" + value + " " + operand.toString() + ")";
    }

    @Override
    public String toMiniString() {
        StringBuilder sb = new StringBuilder();
        sb.append(value);
        sb.append(" ");
        if (needBrackets()) {
            sb.append("(");
        }
        sb.append(operand.toMiniString());
        if (needBrackets()) {
            sb.append(")");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UnaryOperator that = (UnaryOperator) o;
        return priority == that.priority
                && Objects.equals(value, that.value)
                && operand.equals(that.operand);
    }

    @Override
    public int hashCode() {
        int operandHash = (operand == null) ? 0 : operand.hashCode();
        return ((((17 + value.hashCode()) * 31 + priority) * 23) + operandHash) * 41;
    }

    public abstract int calculate(int x);

    private boolean needBrackets() {
        if (operand instanceof BinaryOperator op) {
            return op.getPriority() < priority;
        }
        return false;
    }
}
