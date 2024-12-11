package expression.exceptions;

import expression.VariableExpression;

public class CheckedAdd extends CheckedBinaryOperator {
    public CheckedAdd(VariableExpression operand1, VariableExpression operand2) {
        super("+", 1, operand1, operand2, true, true);
    }

    @Override
    public int calculate(int x, int y) {
        checkOverflow(x, y);
        return x + y;
    }

    @Override
    public boolean hasOverflow(int x, int y) {
        return x > 0 ? Integer.MAX_VALUE - x < y: Integer.MIN_VALUE - x > y;
    }
}
