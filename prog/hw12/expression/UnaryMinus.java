package expression;

public class UnaryMinus extends UnaryOperator {
    public UnaryMinus(VariableExpression operand) {
        super("-", 3, operand);
    }

    @Override
    public int calculate(int x) {
        return -x;
    }
}
