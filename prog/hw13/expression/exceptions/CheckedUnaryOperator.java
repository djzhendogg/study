package expression.exceptions;

import expression.UnaryOperator;
import expression.VariableExpression;

public abstract class CheckedUnaryOperator extends UnaryOperator {
    public CheckedUnaryOperator(String value, int priority, VariableExpression operand) {
        super(value, priority, operand);
    }
}
