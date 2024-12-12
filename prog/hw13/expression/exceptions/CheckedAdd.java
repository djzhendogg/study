package expression.exceptions;

import expression.VariableExpression;

public class CheckedAdd extends CheckedBinaryOperator {
    public CheckedAdd(VariableExpression operand1, VariableExpression operand2) {
        super("+", 1, operand1, operand2, true, true);
    }

    @Override
    protected int calculate(int x, int y) {
        checkOverflow(x, y);
        return x + y;
    }

    @Override
    protected boolean hasOverflow(int x, int y) {
        return OverflowConstraints.checkAddOverflow(x, y);
    }
}
