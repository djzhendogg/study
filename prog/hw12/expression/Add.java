package expression;

public class Add extends BinaryOperator {
    public Add(Expression operand1, Expression operand2) {
        super("+", 2, operand1, operand2);
    }

    @Override
    public int calculate(int x, int y) {
        return x + y;
    }
}
