package expression.exceptions;

import expression.VariableExpression;

public class CheckedNegate extends CheckedUnaryOperator {
    public CheckedNegate(VariableExpression operand) {
        super("-", 3, operand);
    }

    @Override
    public int calculate(int x) {
        return -x;
    }

    @Override
    public String toMiniString() {
        StringBuilder sb = new StringBuilder();
        sb.append(value);
        if (!needBrackets()) {
            sb.append(" ");
        }
        if (needBrackets()) {
            sb.append("(");
        }
        sb.append(operand.toMiniString());
        if (needBrackets()) {
            sb.append(")");
        }
        return sb.toString();
    }
}