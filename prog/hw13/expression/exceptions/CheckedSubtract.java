package expression.exceptions;

import expression.VariableExpression;

public class CheckedSubtract extends CheckedBinaryOperator {
    public CheckedSubtract(VariableExpression operand1, VariableExpression operand2) {
        super("-", 1, operand1, operand2, false, true);
    }

    @Override
    public int calculate(int x, int y) {
        checkOverflow(x, y);
        return x - y;
    }

    @Override
    public boolean hasOverflow(int x, int y) {
        return y == Integer.MIN_VALUE ? x <= -1: x > 0 ? Integer.MAX_VALUE - x < -y: Integer.MIN_VALUE - x > -y;
    }
}
