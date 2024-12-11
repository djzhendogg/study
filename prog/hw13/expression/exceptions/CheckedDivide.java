package expression.exceptions;

import expression.VariableExpression;
import expression.exceptions.expresion_exceptions.ZeroDivisionException;

public class CheckedDivide extends CheckedBinaryOperator {
    public CheckedDivide(VariableExpression operand1, VariableExpression operand2) {
        super("/", 2, operand1, operand2, false, false);
    }

    @Override
    public int calculate(int x, int y) {
        if (y == 0) {
            throw new ZeroDivisionException(this.toString());
        }
        checkOverflow(x, y);
        return x / y;
    }

    @Override
    public boolean hasOverflow(int x, int y) {
        return x == Integer.MIN_VALUE && y == -1;
    }
}
