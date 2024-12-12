package expression.exceptions;

import expression.VariableExpression;
import expression.exceptions.expresion_exceptions.NegativeGeomVariable;

public class CheckedRectanglePerimeter extends CheckedBinaryOperator {
    public CheckedRectanglePerimeter(VariableExpression operand1, VariableExpression operand2) {
        super("▯", 2, operand1, operand2, true, true);
    }

    @Override
    protected int calculate(int x, int y) {
        if (x < 0) {
            throw new NegativeGeomVariable(x, this.toString());
        } else if (y < 0) {
            throw new NegativeGeomVariable(y, this.toString());
        }
        checkOverflow(x, y);
        return (x + y) * 2;
    }

    @Override
    protected boolean hasOverflow(int x, int y) {
        if (
                OverflowConstraints.checkMultiplyOverflow(x, 2) ||
                OverflowConstraints.checkMultiplyOverflow(y, 2)
        ) {
            return true;
        }
        return OverflowConstraints.checkAddOverflow(x * 2, y * 2);
    }
}
