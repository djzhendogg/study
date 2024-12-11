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
        int c = x - y;
        if (x > y) {
            return c < 0;
        } else {
            return c > 0;
        }
    }
}
