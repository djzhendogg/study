package expression;

public class Subtract extends BinaryOperator {
    public Subtract(VariableExpression operand1, VariableExpression operand2) {
        super("-", 1, operand1, operand2, false, true);
    }

    public Subtract(Expression operand1, Expression operand2) {
        super("-", 1, operand1, operand2, false, true);
    }

    @Override
    public int calculate(int x, int y) {
        return x - y;
    }
}
