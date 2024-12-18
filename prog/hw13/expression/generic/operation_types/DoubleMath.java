package expression.generic.operation_types;

import expression.exceptions.expresion_exceptions.ConstValueException;

public class DoubleMath implements ArithmeticOperations<Double> {
    @Override
    public Double add(Double left, Double right) {
        return left + right;
    }

    @Override
    public Double substarct(Double left, Double right) {
        return left - right;
    }

    @Override
    public Double multiply(Double left, Double right) {
        return left * right;
    }

    @Override
    public Double divide(Double left, Double right) {
        return left / right;
    }

    @Override
    public Double negate(Double x) {
        return -x;
    }

    @Override
    public Double parse(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new ConstValueException(value, e);
        }
    }
}
