package expression.exceptions;

import expression.VariableExpression;

public class CheckedMultiply extends CheckedBinaryOperator {
    public CheckedMultiply(VariableExpression operand1, VariableExpression operand2) {
        super("*", 2, operand1, operand2, true, true);
    }

    @Override
    public int calculate(int x, int y) {
        checkOverflow(x, y);
        return x * y;
    }

    @Override
    public boolean hasOverflow(int x, int y) {
        int maxVal = Integer.signum(x) == Integer.signum(y) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
//        int minVal = maxVal / x;
//        if (x != 0 && y != 0) {
//            return false;
//        }
//        if (Integer.signum(x) == Integer.signum(y)) {
//            if (y > 0) {
//                return Integer.MAX_VALUE / x >= y; // 50 > 3
//            } else {
//                return Integer.MIN_VALUE / x <= y; // -50 < -3
//            }
//        } else {
//            if (y > 0) {
//                return Integer.MIN_VALUE / x >= y; // 50 > 3
//            } else {
//                return Integer.MAX_VALUE / x <= y; // -50 < -3
//            }
//        }
        return x != 0  && ((y > 0 && maxVal / x > 0 && maxVal / x < y) || (y < 0 && maxVal / x > y));
    }
}
