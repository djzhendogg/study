package expression;

public class Multiply extends BinaryOperator {
    public Multiply(Expression operand1, Expression operand2) {
        super("*", 1, operand1, operand2);
    }

    @Override
    public int calculate(int x, int y) {
        return x * y;
    }
}
