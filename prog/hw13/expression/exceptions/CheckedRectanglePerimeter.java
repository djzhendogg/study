package expression.exceptions;

import expression.VariableExpression;
import expression.exceptions.expresion_exceptions.NegativeGeomVariable;

public class CheckedRectanglePerimeter extends CheckedBinaryOperator {
    public CheckedRectanglePerimeter(VariableExpression operand1, VariableExpression operand2) {
        super("▯", 2, operand1, operand2, true, true);
    }

    @Override
    protected int calculate(int x, int y) {
        if (x < 0) {
            throw new NegativeGeomVariable(x, this.toString());
        } else if (y < 0) {
            throw new NegativeGeomVariable(y, this.toString());
        }
        checkOverflow(x, y);
        return (x + y) * 2;
    }

    @Override
    protected boolean hasOverflow(int x, int y) {
        if (checkMultiplyOverflow(x, 2) || checkMultiplyOverflow(y, 2)) {
            return true;
        }
        return checkSumOverflow(x * 2, y * 2);
    }

    private boolean checkSumOverflow(int x, int y) {
        return x > 0 ? Integer.MAX_VALUE - x < y: Integer.MIN_VALUE - x > y;
    }

    private boolean checkMultiplyOverflow(int x, int y) {
        int maxVal = Integer.signum(x) == Integer.signum(y) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        return x != 0  && ((y > 0 && maxVal / x > 0 && maxVal / x < y) || (y < 0 && maxVal / x > y));
    }
}
