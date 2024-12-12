package expression.exceptions;

import expression.UnaryOperator;
import expression.VariableExpression;
import expression.exceptions.expresion_exceptions.IntOverflowException;

public abstract class CheckedUnaryOperator extends UnaryOperator {
    public CheckedUnaryOperator(String value, int priority, VariableExpression operand) {
        super(value, priority, operand);
    }

    public abstract boolean hasOverflow(int x);

    public void checkOverflow(int x) {
        if (hasOverflow(x)) {
            throw new IntOverflowException(this.toString());
        }
    }
}
