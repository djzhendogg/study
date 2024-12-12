package expression.exceptions;

import expression.VariableExpression;

public class CheckedMultiply extends CheckedBinaryOperator {
    public CheckedMultiply(VariableExpression operand1, VariableExpression operand2) {
        super("*", 2, operand1, operand2, true, true);
    }

    @Override
    protected int calculate(int x, int y) {
        checkOverflow(x, y);
        return x * y;
    }

    @Override
    protected boolean hasOverflow(int x, int y) {
        return OverflowConstraints.checkMultiplyOverflow(x, y);
    }
}
