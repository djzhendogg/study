package expression.exceptions;

import expression.VariableExpression;
import expression.exceptions.expresion_exceptions.NegativeGeomVariable;

public class CheckedTriangleArea extends CheckedBinaryOperator {
    public CheckedTriangleArea(VariableExpression operand1, VariableExpression operand2) {
        super("◣", 2, operand1, operand2, true, true);
    }

    @Override
    protected int calculate(int x, int y) {
        if (x < 0) {
            throw new NegativeGeomVariable(x, this.toString());
        } else if (y < 0) {
            throw new NegativeGeomVariable(y, this.toString());
        }
        checkOverflow(x, y);
        if (y % 2 == 0 || x % 2 == 1 && y > x) {
            int z = x;
            x = y;
            y = z;
        }
        int result = x / 2 * y;
        if (x % 2 == 1 && y % 2 == 1 && y != 1) {
            result += 1;
        }
        return result;
    }

    @Override
    protected boolean hasOverflow(int x, int y) {
        if (x == 0 || y == 0) {
            return false;
        }
        int biggest = x > y ? x : y;
        int lowest = x <= y ? x : y;
        boolean result = checkDivideOverflow(biggest, 2);
        if (!result) {
            result = checkMultiplyOverflow(biggest / 2, lowest);
        }
        return result ;
    }

    private boolean checkDivideOverflow(int x, int y) {
        return x == Integer.MIN_VALUE && y == -1;
    }

    private boolean checkMultiplyOverflow(int x, int y) {
        int maxVal = Integer.signum(x) == Integer.signum(y) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        return x != 0  && ((y > 0 && maxVal / x > 0 && maxVal / x < y) || (y < 0 && maxVal / x > y));
    }
}
