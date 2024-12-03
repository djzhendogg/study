package expression;

public class Add extends BinaryOperator {
    public Add(VariableExpression operand1, VariableExpression operand2) {
        super("+", 1, operand1, operand2, true, true);
    }

    public Add(Expression operand1, Expression operand2) {
        super("+", 1, operand1, operand2, true, true);
    }

    @Override
    public int calculate(int x, int y) {
        return x + y;
    }
}
