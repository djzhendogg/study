package expression.generic.operation_types;

import expression.exceptions.expresion_exceptions.ConstValueException;
import expression.exceptions.expresion_exceptions.IntOverflowException;
import expression.exceptions.expresion_exceptions.ZeroDivisionException;
import expression.generic.expressions.MathConstraints;

public class IntMath implements ArithmeticOperations<Integer> {
    @Override
    public Integer add(Integer left, Integer right) {
        validateOverflow(MathConstraints.checkAddOverflow(left, right));
        return left + right;
    }

    @Override
    public Integer substarct(Integer left, Integer right) {
        validateOverflow(MathConstraints.checkSubtractOverflow(left, right));
        return left - right;
    }

    @Override
    public Integer multiply(Integer left, Integer right) {
        validateOverflow(MathConstraints.checkMultiplyOverflow(left, right));
        return left * right;
    }

    @Override
    public Integer divide(Integer left, Integer right) {
        if (right == 0) {
            throw new ZeroDivisionException(this.toString());
        }
        validateOverflow(MathConstraints.checkDivideOverflow(left, right));
        return left / right;
    }

    @Override
    public Integer negate(Integer x) {
        validateOverflow(MathConstraints.checkNegativeOverflow(x));
        return -x;
    }

    @Override
    public Integer parse(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new ConstValueException(value, e);
        }
    }

    private void validateOverflow(boolean constraint) {
        if (constraint) {
            throw new IntOverflowException(this.toString());
        }
    }
}
