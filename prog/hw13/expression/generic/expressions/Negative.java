package expression.generic.expressions;

import expression.generic.operation_types.ArithmeticOperations;

public class Negative<T extends Number> extends UnaryOperator<T> {
    public Negative(TripleExpression<T> operand, ArithmeticOperations<T> operations) {
        super("-", 3, operand, operations);
    }

    @Override
    protected T calculate(T x) {
        return operations.negate(x);
    }
}
